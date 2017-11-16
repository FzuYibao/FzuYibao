# Android项目代码编写规范


---
由于后台和安卓端的开发在于数据的衔接，而API由后台开发人员直接给出，故PHP的代码规范和JAVA的代码规范独立开来，但有必要对数据变量做个统一，故我们制定了的JAVA和PHP开发人员应该共同遵循的编码规范：
**数据库表和字段/变量的命名均以小写字母+下划线命名，例如ci_suer表，user_name字段，avatar_path（头像路径）**

##Android编码规范

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">包命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">包（packages）：采用反域名命名规则，全部使用小写字母。一级包名为com，二级包名为xxx（可以是公司域名或者个人命名），三级包名根据应用进行命名，四级包名为模块名或层级名。</span>

<table border="1" cellspacing="0" cellpadding="0" style="color:rgb(0,0,0); line-height:24.0499992370605px; font-family:Arial; font-size:14px">

<tbody>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; color:white; background:transparent">**包名**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; color:white; background:transparent">**说明**</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.activities**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">页面用到的Activity类（activities层级用户界面）</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.fragment**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">页面用到的Fragment类</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.base**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">页面中每个Activity类共享的可以写成一个BaseActivity类（基础共享的类）</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.adapter**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">页面用到的Adapter类（适配器的类）</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.utils**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">此包中包含：公共工具方法类（包含日期、网络、存储、日志等工具类）</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.bean**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**（model/domain均可，个人喜好）**</span>

</td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">实体类</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.db**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">数据库操作</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.view（或者.ui）**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">自定义的View类等</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.service**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">Service服务</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**com.xxx.应用名称缩写.broadcast**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">Broadcast服务</span></td>

</tr>

</tbody>

</table>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">类命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">类（classes）：名词，采用大驼峰命名法，尽量避免缩写，除非该缩写是众所周知的，比如HTML，URL,如果类名称包含单词缩写，则单词缩写的每个字母均应大写。</span>

<table border="1" cellspacing="0" cellpadding="0" style="color:rgb(0,0,0); line-height:24.0499992370605px; font-family:Arial; font-size:14px">

<tbody>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">类</span>**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">描述</span>**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">例如</span>**</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**Application类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">Application为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">XXXApplication</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**Activity类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">Activity为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">闪屏页面类</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">SplashActivity</span>

</td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**解析类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">Handler为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**公共方法类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">Utils或Manager为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">线程池管理类：ThreadPoolManager</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">日志工具类：LogUtils</span>

</td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**数据库类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">以DBHelper后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">MySQLiteDBHelper</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**Service类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">以Service为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">播放服务：PlayService</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**BroadcastReceiver类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">以Broadcast为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">时间通知：</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">TimeBroadcast</span>

</td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**ContentProvider类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">以Provider为后缀标识</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">单词内容提供者：DictProvider</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**直接写的共享基础类**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">以Base为前缀</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">BaseActivity,</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">BaseFragment</span>

</td>

</tr>

</tbody>

</table>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">变量命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">变量（variables）采用小驼峰命名法。类中控件名称必须与xml布局id保持一致。</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:red; background:transparent">公开的常量</span>：定义为静态final，名称全部大写。eg: public staticfinal String ACTION_MAIN=”android.intent.action.MAIN”;</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:red; background:transparent">静态变量</span>：名称以s开头 eg：private static long sInstanceCount = 0;</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:red; background:transparent">非静态的私有变量、</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:red; background:transparent">protected</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:red; background:transparent">的变量</span>：以m开头，eg：private Intent mItent;</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">接口命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">接口（interface）：命名规则与类一样采用大驼峰命名法，多以able或ible结尾，eg：interface Runable; interface Accessible;</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">方法命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">方法（methods）：动词或动名词，采用小驼峰命名法，eg：onCreate(),run();</span>

<table border="1" cellspacing="0" cellpadding="0" style="color:rgb(0,0,0); line-height:24.0499992370605px; font-family:Arial; font-size:14px">

<tbody>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; color:white; background:transparent">**方法**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; color:white; background:transparent">**说明**</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**initXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">初始化相关方法，使用init为前缀标识，如初始化布局initView()</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**isXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">checkXX()方法返回值为boolean型的请使用is或check为前缀标识</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**getXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">返回某个值的方法，使用get为前缀标识</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**processXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">对数据进行处理的方法，尽量使用process为前缀标识</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**displayXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">弹出提示框和提示信息，使用display为前缀标识</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**saveXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">与保存数据相关的，使用save为前缀标识</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**resetXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">对数据重组的，使用reset前缀标识</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**clearXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">清除数据相关的</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**removeXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">清除数据相关的</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**drawXXX()**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">绘制数据或效果相关的，使用draw前缀标识</span></td>

</tr>

</tbody>

</table>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">布局文件命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> 全部小写，采用下划线命名法</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">1)．contentview命名, Activity默认布局，以去掉后缀的Activity类进行命名。不加后缀：</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**功能模块.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**eg：main.xml、more.xml、settings.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">       或者：activity_功能模块.xml</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">eg：**activity_main.xml、activity_more.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">2)．Dialog命名：dialog_描述.xml</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">eg：**dlg_hint.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">3)．PopupWindow命名：ppw_描述.xml</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">       eg：**ppw_info.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">4). 列表项命名listitem_描述.xml</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">eg：**listitem_city.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">5)．包含项：include_模块.xml</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">eg：**include_head.xml、include_bottom.xml**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">6)．adapter的子布局：**功能模块**_item.xml</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">eg：**main_item.xml、**</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">资源id命名规范</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> <span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">命名模式为：</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">view</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">缩写</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">_</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">模块名称</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">_view</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">的逻辑名称</span></span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">view</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">的缩写详情如下：</span></span>

<table border="1" cellspacing="0" cellpadding="0" style="color:rgb(0,0,0); line-height:24.0499992370605px; font-family:Arial; font-size:14px">

<tbody>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:white; background:transparent">控件</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:white; background:transparent">缩写</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">LayoutView</span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(51,51,51); background:transparent">lv</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">RelativeView</span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(51,51,51); background:transparent">rv</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(51,51,51); background:transparent">TextView</span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(51,51,51); background:transparent">tv</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">Button</span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">btn</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ImageButton</span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">imgBtn</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ImageView</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">         </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">iv</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">CheckBox</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">           </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">cb</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">RadioButton</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">        </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">rb</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">analogClock</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">        </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">anaClk</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">DigtalClock</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">        </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">dgtClk</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">DatePicker</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">         </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">dtPk</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">EditText</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">           </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">edtTxt</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">TimePicker</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">         </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">tmPk</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">toggleButton</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">       </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">tglBtn</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ProgressBar</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent"> </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">proBar</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">SeekBar</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">                            </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">skBar</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">AutoCompleteTextView</span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">autoTxt</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ZoomControls</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">       </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">zmCtl</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">VideoView</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">          </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">vdoVi</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">WdbView</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">            </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">webVi</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">RantingBar</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">         </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">ratBar</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">Tab                </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">tab</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">Spinner            </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">spn</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">Chronometer        </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">cmt</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ScollView</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">          </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">sclVi</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">TextSwitch</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">         </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">txtSwt</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ImageSwitch</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">        </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">imgSwt</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">listView</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">           </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">lVi</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent"> </span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">或则</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">lv</span></span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">ExpandableList</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">     </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">epdLt</span>

</td>

</tr>

<tr>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">MapView</span><span style="margin:0px; padding:0px; border:0px; font-size:16px; color:rgb(70,70,70); background:transparent">            </span>**</span>

</td>

<td>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; color:rgb(70,70,70); background:transparent">mapVi</span>

</td>

</tr>

</tbody>

</table>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">动画文件命名</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> 动画文件（anim文件夹下）：全部小写，采用下划线命名法，加前缀区分。</span>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">//前面为动画的类型，后面为方向</span>

<table width="431" border="1" cellspacing="0" cellpadding="0" style="color:rgb(0,0,0); line-height:24.0499992370605px; font-family:Arial; font-size:14px">

<tbody>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">动画命名例子</span>**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">规范写法</span>**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">备注</span>**</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**fade_in**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">淡入</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**fade_out**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">淡出</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**push_down_in**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">从下方推入</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**push_down_out**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">从下方推出</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**push_left**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">推像左方</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**slide_in_from_top**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">从头部滑动进入</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**zoom_enter**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">变形进入</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**slide_in**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">滑动进入</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**shrink_to_middle**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">中间缩小</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

</tbody>

</table>

<span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">图片资源文件命名</span>

<table border="1" cellspacing="0" cellpadding="0" style="color:rgb(0,0,0); line-height:24.0499992370605px; font-family:Arial; font-size:14px">

<tbody>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">命名</span>**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**<span style="margin:0px; padding:0px; border:0px; font-size:16px; color:white; background:transparent">说明</span>**</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**bg_xxx**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">这种图片一般那些比较大的图片，比如作为某个Activity的背景等</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**btn_xxx**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">按钮，一般用于按钮，而且这种按钮没有其他状态</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**ic_xxx**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">图标，一般用于单个图标，比如启动图片ic_launcher</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**bg_描述_状态1[_状态2]**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">用于控件上的不同状态</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**btn_描述_状态1[_状态2]**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">用于按钮上的不同状态</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">**chx_描述_状态1[_状态2]**</span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent">选择框，一般有2态和4态</span></td>

</tr>

<tr>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"></span></td>

<td valign="top"><span style="margin:0px; padding:0px; border:0px; font-size:14px; background:transparent"> </span></td>

</tr>

</tbody>

</table>
