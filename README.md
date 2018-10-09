[TOC]

# GSNettyMVC

### 1.基本功能

1. 基于 netty 的 web mvc 组件
2. 请求参数支持 json 、form 、serializer 形式
3. 客户端服务端间交互支持 session 域

### 2.基本使用

客户端代码：

    public class ClientTest {
	
    	private ClientNettyProcessor processor;
    
    	
    	@Before
    	public void init() throws Exception{
    		
    		processor = GoNettyProcessor.clientBuilder()
    						.host("127.0.0.1")
    						.port(8080)
    						.build();
    		processor.start();
    		
    	}
    	
    	@Test
    	public void test(){
    		
    		getDeviceInfo();
    		login();
    		getDeviceInfo();
    	
    	}
    	
    	public void login(){
    		
    		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
    				.requestType(BodyTypeInface.FORM)
    				.url("test/login")
    				.build();
    		
    		DefaultDTO dto = DefaultDTO.buidler()
    				.msgType(MsgTypeInface.REQUEST)
    				.header(requestHeader)
    				.keyValue("username", "xiaomo")
    				.keyValue("password", "123456")
    				.build();
    		
    		processor.send(dto);
    		
    	}
    	
    	public void getDeviceInfo(){
    		
    		ParamsModel model = new ParamsModel();
    		model.setDeviceProducer("高新兴科技");
    		model.setDeviceType(123);
    		
    		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
    				.url("test/deviceInfo")
    				.requestType(BodyTypeInface.JSON)
    				.build();
    		
    		DefaultDTO dto = DefaultDTO.buidler()
    				.msgType(MsgTypeInface.REQUEST)
    				.header(requestHeader)
    				.body(model)
    				.build();
    		
    		processor.send(dto);
    		
    	}
	}

服务端代码：

    public void startServer() throws Exception{
		
		ServerBuilder builder =  GoNettyProcessor.serverBuilder();
		builder.build().start();
		
	}


    @GoController
    @GoRequestMapping(path = "/test/")
    public class TestController {
    	
    	@GoRequestMapping(path = "login")
    	public ResponseModel testLogin(String username,String password,GoSession session){
    
    		LoggerUtils.info("用户登录-{}-{}",username,password);
    		// ignore login logic ...
    		session.setAttribute("login", true);
    		ResponseModel responseModel = new ResponseModel();
    		responseModel.setMessage("登录成功");
    		
    		return responseModel;
    		
    	}
    	
    	
    	@GoRequestMapping(path = "deviceInfo")
    	public ResponseModel testGetDeviceInfo(GoSession session,DeviceModel model){
    
    		LoggerUtils.info("接受到请求参数：-{}",JsonUtils.toJsonString(model));
    		
    		ResponseModel responseModel = new ResponseModel();
    		Boolean loginFlag = (Boolean) session.getAttribute("login");
    		if(loginFlag){
    			
    			model.setDeviceId("666");
    			model.setLatitude(123.43);
    			model.setLongitude(234.56);
    			model.setDeviceName("gps定位设备");
    			model.setDeviceType(10001);
    			responseModel.setModel(model);
    		}else{
    			responseModel.setMessage("访问设备信息需要登录授权");
    		}
    		return responseModel;
    		
    	}
    }

### 3.主体结构

GSNettyMVC 的结构主要分为4大组件，分别是：

+ NettyProcessor
+ RequestAccepter
+ RequestDispatcher
+ BusinessController

4大组件由GSNettyMVC组织串联起来，完成消息的解编码、传递、分发等功能。

![主体结构][1]
                             |
[1]: ./images/structure.png "structure.png"