# 小米汽车电池预警 项目介绍

BMS系统是智能化管理及维护各个电池单元，防止电池出现过充电和过放电、延长电池的使用寿命、监控电池状态的系统。在BMS系统中存在大量电池各种信号的规则管理以及监控，良好的是处理信号，并且根据规则，生成相关预警信息，能够极大提升用户体验。为此需要大家完成一套支持规则配置、信号预警的系统，来解决电池各种突发情况和提升用户体验。

# 本地运行

## 必要的软件

- Java Development Kit (JDK) 17 或更新版本

- Apache Maven

- MySQL Server

- Redis

## 导入数据库bms

连接MySQL，Navicat图形化界面/命令行 运行 bms.sql，导入数据库bms

## 修改application.properties配置文件

### 解压 JAR 文件：

- 右键点击 BatteryWarningSystem-0.0.1-SNAPSHOT.jar。

- 选择 7-Zip -> Extract to "BatteryWarningSystem-0.0.1-SNAPSHOT"。

### 修改 application.properties 文件：

- 导航到解压后的目录BatteryWarningSystem-0.0.1-SNAPSHOT\BOOT-INF\classes\ application.properties。

- 打开并编辑 application.properties 文件(连接到本地MySQL和redis)，保存修改。

## 重新打包 JAR 文件：

- 打开 7-Zip 文件管理器。
  
- 导航到 ..\BatteryWarningSystem-0.0.1-SNAPSHOT\。
  
- 按 Ctrl+A 选择所有文件和文件夹。
  
- 点击 添加（Add）。
  
- 在 添加到归档文件（Add to Archive）窗口中，设置归档格式为 zip，并将文件扩展名改为 .jar，如 BatteryWarningSystem-0.0.1-SNAPSHOT.jar。
  点击 确定。
  
## 本地运行打包好的jar包

运行以下命令行：

```sh
java -jar BatteryWarningSystem-0.0.1-SNAPSHOT.jar
```
# License

[PROPRIETARY License](https://github.com/numbbbbbplus/XiaomiEV-Battery-Warning-System/blob/main/PROPRIETARY-LICENSE.txt)
