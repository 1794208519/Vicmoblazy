//所有应用Ajax Url统一设置的配置信息代码
var API_APP_BASE = "http://localhost:8988/";

//获取应用的一些信息
var API_APP_GETAPPINFO = API_APP_BASE + "api/default/getappinfo";
//登录系统
var API_ACCOUNT_LOGIN = API_APP_BASE + "api/account/login";
//登出
var API_ACCOUNT_LOGOUT = API_APP_BASE + "api/account/logout";
//获取当前登录信息
var API_ACCOUNT_GETCURRENTLOGIN = API_APP_BASE + "api/account/getcurrentlogin";


//==========通用功能==============
//获取所有省份
var API_COMMON_GETPROVINCES = API_APP_BASE + "api/area/getprovince";
//获取省份下所有城市
var API_COMMON_GETCITIES = API_APP_BASE + "api/area/GetCityByProvince";
//获取城市下所有区域
var API_COMMON_GETDISCTRICTS = API_APP_BASE + "api/area/GetDistrictByCity";
//获取商家大类型
var API_COMMON_GETPROVIDERTYPES = API_APP_BASE + "api/common/GetProviderTypes";
//获取商家小类型
var API_COMMON_GETPROVIDERSUBTYPES = API_APP_BASE + "api/common/GetProviderSubTypesByParentTypeId";
//获取商品类型
var API_COMMON_GETSERVICETYPES = API_APP_BASE + "api/common/GetServiceTypes";
//==========通用功能==============


//==========用户管理功能==============
var API_USER_GETALL = API_APP_BASE + "api/user/getallusers";
var API_USER_GETALLROLE = API_APP_BASE + "api/user/getallroles";
var API_USER_CREATE = API_APP_BASE + "api/user/createuser";
var API_USER_SAVE = API_APP_BASE + "api/user/saveuser";
var API_USER_DELETE = API_APP_BASE + "api/user/deleteuser";
var API_USER_GETCURRENT = API_APP_BASE + "api/user/getcurrentuser";
//==========用户管理功能==============

//==========File WebApi Route Start==============
var API_FILE_UPLOAD = API_APP_BASE + "api/file/upload";
var API_FILE_GETIMAGE = API_APP_BASE + "api/file/getimage";
//==========File WebApi Route End==============

//==========代理商管理功能==============
var API_AGENT_GETALL = API_APP_BASE + "api/agent/getagent";
var API_AGENT_CREATE = API_APP_BASE + "api/agent/createagent";
var API_AGENT_SAVE = API_APP_BASE + "api/agent/saveagent";
var API_AGENT_DELETE = API_APP_BASE + "api/agent/deleteagent";
//==========代理商管理功能==============

//==========商家管理功能==============
var API_PROVIDER_GETALL = API_APP_BASE + "api/provider/getall";
var API_PROVIDER_GETSINGLEPROVIDER = API_APP_BASE + "api/provider/GetProviderDetailById_Web?id=";
var API_PROVIDER_CREATE = API_APP_BASE + "api/provider/create";
var API_PROVIDER_SAVE = API_APP_BASE + "api/provider/save";
var API_PROVIDER_DELETE = API_APP_BASE + "api/provider/delete";
//==========商家管理功能==============

//==========客户管理功能==============
var API_CUSTOMER_GETALL = API_APP_BASE + "api/Customer/GetCustomer";
var API_CUSTOMER_CREATE = API_APP_BASE + "api/Customer/create";
var API_CUSTOMER_SAVE = API_APP_BASE + "api/Customer/save";
var API_CUSTOMER_DELETE = API_APP_BASE + "api/Customer/delete";
var API_CUSTOMER_CREATEVIPCARDHISTORY = API_APP_BASE + "api/Customer/CreateVipCardUsageHistory";
var API_CUSTOMER_AGENT = API_APP_BASE + "api/Customer/CreateCustomerAgent";
var API_CUSTOMER_LOSSCARD_SMS = API_APP_BASE + "api/Customer/SendDisabledVipCardSMS";
var API_CUSTOMER_DISABLEDVIPCARD_SMS = API_APP_BASE + "api/Customer/DisabledVipCard";
//==========客户管理功能==============

//==========商品管理功能==============
var API_SERVICE_GETALL = API_APP_BASE + "api/service/getall?providerId=";
var API_SERVICE_CREATE = API_APP_BASE + "api/service/create";
var API_SERVICE_SAVE = API_APP_BASE + "api/service/save";
var API_SERVICE_DELETE = API_APP_BASE + "api/service/delete";
//==========商家管理功能==============

//==========预定管理功能==============
var API_ORDER_GETALL = API_APP_BASE + "api/Order/GetOrder";
var API_ORDER_GETPENDING = API_APP_BASE + "api/Order/GetBeginOrder";
var API_ORDER_CONFIRM = API_APP_BASE + "api/Order/ConfirmOrderWeb";
var API_ORDER_CANCEL = API_APP_BASE + "api/Order/CancelOrderWeb";
//==========预定管理功能==============

//==========消费管理功能==============
var API_PAYMENT_GETALL = API_APP_BASE + "api/payment/GetPayment";
var API_PAYMENT_PAYTOPROVIDER = API_APP_BASE + "api/pay/PayToProvider";
var API_PAYMENT_DOENLOAD = API_APP_BASE + "api/payment/GeneratePaymentFile";
//==========消费管理功能==============