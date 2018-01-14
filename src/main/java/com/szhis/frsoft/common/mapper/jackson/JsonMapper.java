package com.szhis.frsoft.common.mapper.jackson;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.szhis.frsoft.common.utils.DateUtils;

public class JsonMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
	private SimpleFilterProvider simpleFilterProvider;

	public JsonMapper() {
		// 忽略json中不在实体中存在的属性
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 用于反序列化时解析@JsonIdentityInfo定义的属性时忽略未传ID的情况
		configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
		configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		SimpleModule module = new SimpleModule();
		module.addSerializer(Date.class, new CustomDateSerializer(DateUtils.DATE_FORMAT_LONG_DEFAULT));
		//module.addDeserializer(Date.class, new CustomDateDeserializer(dateformat));
		module.addDeserializer(Date.class, new CustomDateDeserializer());
		registerModule(module);
		/*simpleFilterProvider = new SimpleFilterProvider();
		setFilterProvider(simpleFilterProvider);
		simpleFilterProvider.setFailOnUnknownId(false);*/
		/**
		 *  如果类上定义了@JsonFilter注解，序列化类的实例时没有调用addFilter为过滤器设置过滤属性，
		 *  则会报下面的错误。如果要忽略该错误，则需要调用simpleFilterProvider.setFailOnUnknownId(false);
		 *  java.lang.RuntimeException: Can not resolve PropertyFilter with id 'none'; no FilterProvider configured
		 */
	}

	//hibernate4Module可传null,hibernate延迟加载属性json序列化忽略,并保留对@Transient属性json序列化和反序列化
	public JsonMapper(Hibernate4Module hibernate4Module) {
		this();
		if (hibernate4Module == null) {
			hibernate4Module = new Hibernate4Module();
		}
		hibernate4Module.configure(Feature.USE_TRANSIENT_ANNOTATION, false);
		//解决hibernate4Module忽略延迟加载对象返回null值问题，对于@ManyToOne延迟加载属性，不应该忽略外键id值。
		hibernate4Module.configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
		registerModule(hibernate4Module);
	}

	public JsonMapper(EntityManagerFactory entityManagerFactory) {
		this();
		HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
		/*
		 * jackson-datatype-hibernate4从2.5版本开始Hibernate4Module重载了多个构造函数，必须把sessionFactoryImpl作为mapping
		 * 传给Hibernate4Module构造函数，否则延迟加载属性主键值不会按主键名构造，而会按类名构造。比如：MZGHJL的brjbxx属性
		 * 会序列化为：{lsh:1, brjbxx:{com.szhis.brjbxx:1}}，而不是我们所要的:{lsh:1, brjbxx:{id:1}}
		 */
		Hibernate4Module hibernate4Module = new Hibernate4Module(sessionFactoryImpl, sessionFactoryImpl);
		hibernate4Module.configure(Feature.USE_TRANSIENT_ANNOTATION, false);
		//解决hibernate4Module忽略延迟加载对象返回null值问题，对于@ManyToOne延迟加载属性，不应该忽略外键id值。
		hibernate4Module.configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
		registerModule(hibernate4Module);
	}

	public JsonMapper(Include include) {
		this();
		// 设置输出时包含属性的风格
		if (include != null) {
			setSerializationInclusion(include);
		}
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		/*setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));*/
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
	 */
	public static JsonMapper nonEmptyMapper() {
		return new JsonMapper(Include.NON_EMPTY);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
	 */
	public static JsonMapper nonDefaultMapper() {
		return new JsonMapper(Include.NON_DEFAULT);
	}

	public JsonMapper addFilter(String filterName, String... properties) {
		if (simpleFilterProvider == null) {
			simpleFilterProvider = new SimpleFilterProvider();
			setFilterProvider(simpleFilterProvider);
			simpleFilterProvider.setFailOnUnknownId(false); // 没有找到过滤器ID传入不报错
		}
		simpleFilterProvider.addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(properties));
		return this;
	}

	public JsonMapper addFilterByPOJOClass(Class<?> pojoClass, String... properties) {
		return addFilter(getPOJOClassFilterName(pojoClass), properties);
	}

	/**
	 * Object可以是POJO，也可以是Collection或数组。
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {
		try {
			return writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String toJsonString(Object object) {
		JsonMapper jsonMapper = new JsonMapper();
		return jsonMapper.toJson(object);

	}

	/**
	 * 
	 * @param object
	 * @param filterName
	 * @param filterproperties 需要忽略的属性名，只能是一级属性名，不能是多级
	 * @return
	 */
	public static String toJsonStringWithFilter(Object object, String filterName, String... filterproperties) {
		JsonMapper jsonMapper = new JsonMapper();
		jsonMapper.addFilter(filterName, filterproperties);
		return jsonMapper.toJson(object);
	}

	//判断某个pojo类是否使用了@JsonFilter注释
	public boolean isJsonFilterAnnotationPresent(Class<?> pojoClass) {
		return pojoClass.isAnnotationPresent(JsonFilter.class);
	}

	public String getPOJOClassFilterName(Class<?> pojoClass) {
		if (isJsonFilterAnnotationPresent(pojoClass)) {
			return ((JsonFilter) pojoClass.getAnnotation(JsonFilter.class)).value();
		} else {
			return null;
		}
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
	 * 
	 * @see #fromJson(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if ((jsonString == null) || (jsonString.length() == 0)) {
			return null;
		}

		try {
			return readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
	 * 
	 * @see #createCollectionType(Class, Class...)
	 */
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if ((jsonString == null) || (jsonString.length() == 0)) {
			return null;
		}

		try {
			return readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Jackson 处理复杂类型(List,map)两种方法 
	 * 方法一:
	 *  String jsonString="[{'id':'1'},{'id':'2'}]";
	 *  ObjectMapper mapper = new ObjectMapper();
	 *  JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Bean.class);  
	 *  //如果是Map类型  mapper.getTypeFactory().constructParametricType(HashMap.class,String.class, Bean.class);
	 *  List<Bean> lst =  (List<Bean>)mapper.readValue(jsonString, javaType);  
	 *  方法二: 
	 *  List<Bean> beanList = mapper.readValue(jsonString, new TypeReference<List<Bean>>() {})
	 *   
	 * 构造Collection类型.
	 */
	@SuppressWarnings("rawtypes")
	public JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
		return getTypeFactory().constructCollectionType(collectionClass, elementClass);
	}

	/**
	 * 构造Map类型.
	 */
	@SuppressWarnings("rawtypes")
	public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
		return getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
	}

	/**
	 * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
	 */
	public void update(String jsonString, Object object) {
		try {
			readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
	}

	/**
	 * 輸出JSONP格式數據.
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 設定是否使用Enum的toString函數來讀寫Enum,
	 * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
	 * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
	 */
	public void enableEnumUseToString() {
		enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
	}

	/**
	 * 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合。
	 * 默认会先查找jaxb的annotation，如果找不到再找jackson的。
	 */
	public void enableJaxbAnnotation() {
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		registerModule(module);
	}
}
