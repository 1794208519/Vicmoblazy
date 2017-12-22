package com.jeeplus.modules.sys.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigurationFileHelper {
	// 第三方平台账号信息配置
	private static final String COMPONENT_APPID = "component_appid";
	private static final String COMPONENT_APPSECRET = "component_appsecret";
	private static final String COMPONENT_ENCODINGAESKEY = "component_encodingaeskey";
	private static final String COMPONENT_TOKEN = "component_token";

	// 支付相关信息
	private static final String PAY_APPID = "pay_appid";
	private static final String PAY_MCH_ID = "pay_mch_id";
	private static final String PAY_API_KEY = "pay_api_key";
	private static final String PAY_CERTIFICATEPATH = "pay_certificatePath";
	private static final String SAVE_CERTIFICATEPATH = "saveCertificatePath";

	// 项目调用域名链接配置
	private static final String SERVICE_URL = "serviceUrl";

	// 打印机接口路径
	private static final String FEIEAPIURL = "feieApiUrl";
	private static final String FEIEUSER = "feieUSER";
	private static final String FEIEUKEY = "feieUKEY";

	// 图片域名
	private static final String PICTURE_URL = "pictureUrl";

	// 图片保存路径
	private static final String SAVEPIC_PATH = "savePicPath";

	// 同步php的图片域名路径
	private static final String syncForPhpUrl_URL = "syncForPhpUrl";
	// 同步estate的图片域名路径
	private static final String syncForEstateUrl_URL = "syncForEstateUrl";

	// 小程序地址（文件存放和配置文件路径）
	private static final String restaurantMINA_URL = "restaurantMINA";
	private static final String restaurantMINAConfig_URL = "restaurantMINAConfig";
	private static final String vehicleMINA_URL = "vehicleMINA";
	private static final String vehicleMINAConfig_URL = "vehicleMINAConfig";
	private static final String hotelMINA_URL = "hotelMINA";
	private static final String hotelMINAConfig_URL = "hotelMINAConfig";
	private static final String estateMINA_URL = "estateMINA";
	private static final String estateMINAConfig_URL = "estateMINAConfig";
	private static final String shopMINA_URL = "shopMINA";
	private static final String shopMINAConfig_URL = "shopMINAConfig";
	private static final String mallMINA_URL = "mallMINA";
	private static final String mallMINAConfig_URL = "mallMINAConfig";
	// 小程序压缩后存放地址
	private static final String restaurantZipFile_URL = "restaurantZipFile";
	private static final String vehicleZipFile_URL = "vehicleZipFile";
	private static final String hotelZipFile_URL = "hotelZipFile";
	private static final String estateZipFile_URL = "estateZipFile";
	private static final String shopZipFile_URL = "shopZipFile";
	private static final String mallZipFile_URL = "mallZipFile";

	// aes加密秘钥(wxk@abcdefg12345,写死)
	private static final String aesKey_URL = "aesKey";

	private static final String MINATIP = "minaTip";

	// 支付相关
	private static String pay_appid;
	private static String pay_mch_id;
	private static String pay_api_key;
	private static String pay_certificatePath;
	private static String saveCertificatePath;

	private static String serviceUrl;
	private static String pictureUrl;
	private static String savePicPath;
	private static String component_appid;
	private static String component_appsecret;
	private static String component_encodingaeskey;
	private static String component_token;

	// 打印机
	private static String feieApiUrl;
	private static String feieUSER;
	private static String feieUKEY;

	// 同步php的图片域名路径
	private static String syncForPhpUrl;
	// 同步estate的图片域名路径
	private static String syncForEstateUrl;
	// 小程序地址
	private static String restaurantMINA;
	private static String restaurantMINAConfig;
	private static String vehicleMINA;
	private static String vehicleMINAConfig;
	private static String hotelMINA;
	private static String hotelMINAConfig;
	private static String estateMINA;
	private static String estateMINAConfig;
	private static String shopMINA;
	private static String shopMINAConfig;
	private static String mallMINA;
	private static String mallMINAConfig;
	// 小程序压缩后存放地址
	private static String restaurantZipFile;
	private static String vehicleZipFile;
	private static String hotelZipFile;
	private static String estateZipFile;
	private static String shopZipFile;
	private static String mallZipFile;
	// aes加密秘钥
	private static String aesKey;
	// 小程序的提醒
	private static String minaTip;

	/**
	 * 初始化
	 */
	static {
		initDBSource();
	}

	private static final void initDBSource() {
		Properties config = new Properties();
		try {
			// 加载配置文件
			String path = ConfigurationFileHelper.class.getResource("/").getPath();
			String websiteURL = ("/" + path + "configuration.properties").replaceFirst("/", "");
			FileInputStream in = new FileInputStream(websiteURL);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in, "utf-8"));
			config.load(bf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 支付相关
		pay_appid = config.getProperty(PAY_APPID);
		pay_mch_id = config.getProperty(PAY_MCH_ID);
		pay_api_key = config.getProperty(PAY_API_KEY);
		pay_certificatePath = config.getProperty(PAY_CERTIFICATEPATH);
		saveCertificatePath = config.getProperty(SAVE_CERTIFICATEPATH);

		serviceUrl = config.getProperty(SERVICE_URL);
		pictureUrl = config.getProperty(PICTURE_URL);
		savePicPath = config.getProperty(SAVEPIC_PATH);

		component_appid = config.getProperty(COMPONENT_APPID);
		component_appsecret = config.getProperty(COMPONENT_APPSECRET);
		component_encodingaeskey = config.getProperty(COMPONENT_ENCODINGAESKEY);
		component_token = config.getProperty(COMPONENT_TOKEN);

		// 打印机
		feieApiUrl = config.getProperty(FEIEAPIURL);
		feieUSER = config.getProperty(FEIEUSER);
		feieUKEY = config.getProperty(FEIEUKEY);

		// 同步php的图片域名路径
		syncForPhpUrl = config.getProperty(syncForPhpUrl_URL);
		// 同步estate的图片域名路径
		syncForEstateUrl = config.getProperty(syncForEstateUrl_URL);
		// 小程序地址
		restaurantMINA = config.getProperty(restaurantMINA_URL);
		restaurantMINAConfig = config.getProperty(restaurantMINAConfig_URL);
		vehicleMINA = config.getProperty(vehicleMINA_URL);
		vehicleMINAConfig = config.getProperty(vehicleMINAConfig_URL);
		hotelMINA = config.getProperty(hotelMINA_URL);
		hotelMINAConfig = config.getProperty(hotelMINAConfig_URL);
		estateMINA = config.getProperty(estateMINA_URL);
		estateMINAConfig = config.getProperty(estateMINAConfig_URL);
		shopMINA = config.getProperty(shopMINA_URL);
		shopMINAConfig = config.getProperty(shopMINAConfig_URL);
		mallMINA = config.getProperty(mallMINA_URL);
		mallMINAConfig = config.getProperty(mallMINAConfig_URL);
		// 小程序压缩后存放地址
		restaurantZipFile = config.getProperty(restaurantZipFile_URL);
		vehicleZipFile = config.getProperty(vehicleZipFile_URL);
		hotelZipFile = config.getProperty(hotelZipFile_URL);
		estateZipFile = config.getProperty(estateZipFile_URL);
		shopZipFile = config.getProperty(shopZipFile_URL);
		mallZipFile = config.getProperty(mallZipFile_URL);
		// aes加密秘钥
		aesKey = config.getProperty(aesKey_URL);

		minaTip = config.getProperty(MINATIP);

	}

	// 打印
	public static String getFeieApiUrl() {
		return feieApiUrl;
	}

	public static String getFeieUSER() {
		return feieUSER;
	}

	public static String getFeieUKEY() {
		return feieUKEY;
	}

	// 支付相关
	public static synchronized String getPayAppId() {
		return pay_appid;
	}

	public static synchronized String getPayMchId() {
		return pay_mch_id;
	}

	public static synchronized String getPayApiKey() {
		return pay_api_key;
	}

	public static synchronized String getPayCertificatePath() {
		return pay_certificatePath;
	}

	public static synchronized String getSaveCertificatePath() {
		return saveCertificatePath;
	}

	public static synchronized String getServiceUrl() {
		return serviceUrl;
	}

	public static synchronized String getPictureUrl() {
		return pictureUrl;
	}

	public static synchronized String getSavePicPath() {
		return savePicPath;
	}

	public static synchronized String getComponentAppid() {
		return component_appid;
	}

	public static synchronized String getComponentAppsecret() {
		return component_appsecret;
	}

	public static synchronized String getComponentEncodingaeskey() {
		return component_encodingaeskey;
	}

	public static synchronized String getComponentToken() {
		return component_token;
	}

	// 同步php的图片域名路径
	public static synchronized String getSyncForPhpUrl() {
		return syncForPhpUrl;
	}

	// 同步php的图片域名路径
	public static synchronized String getSyncForEstateUrl() {
		return syncForEstateUrl;
	}

	// 小程序地址
	public static synchronized String getRestaurantMINA() {
		return restaurantMINA;
	}

	public static synchronized String getRestaurantMINAConfig() {
		return restaurantMINAConfig;
	}

	public static synchronized String getVehicleMINA() {
		return vehicleMINA;
	}

	public static synchronized String getVehicleMINAConfig() {
		return vehicleMINAConfig;
	}

	public static synchronized String getHotelMINA() {
		return hotelMINA;
	}

	public static synchronized String getHotelMINAConfig() {
		return hotelMINAConfig;
	}

	public static synchronized String getEstateMINA() {
		return estateMINA;
	}

	public static synchronized String getEstateMINAConfig() {
		return estateMINAConfig;
	}

	public static synchronized String getShopMINA() {
		return shopMINA;
	}

	public static synchronized String getShopMINAConfig() {
		return shopMINAConfig;
	}

	public static synchronized String getMallMINA() {
		return mallMINA;
	}

	public static synchronized String getMallMINAConfig() {
		return mallMINAConfig;
	}

	// 小程序压缩后存放地址
	public static synchronized String getRestaurantZipFile() {
		return restaurantZipFile;
	}

	public static synchronized String getVehicleZipFile() {
		return vehicleZipFile;
	}

	public static synchronized String getHotelZipFile() {
		return hotelZipFile;
	}

	public static synchronized String getEstateZipFile() {
		return estateZipFile;
	}

	public static synchronized String getShopZipFile() {
		return shopZipFile;
	}

	public static synchronized String getMallZipFile() {
		return mallZipFile;
	}

	// aes加密秘钥
	public static synchronized String getAesKey() {
		return aesKey;
	}

	public static synchronized String getMinaTip() {
		return minaTip;
	}

}
