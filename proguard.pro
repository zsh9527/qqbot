# https://www.guardsquare.com/manual/configuration/usage
# Keep the main class entry point.
-keep public class com.zsh.qqbot.QQbotApplication {
  public static void main(java.lang.String[]);
}
-keep public class com.zsh.qqbot.log.GuiAppender
# 保留方法名称
-keepclassmembers class com.zsh.qqbot.dao.* {
   <methods>;
}
# 保留属性名称
-keepclassmembers class com.zsh.qqbot.pojo.** {
    <fields>;
}
# 序列化类保留属性名称及set方法
-keepclassmembers class com.zsh.qqbot.pojo.serialize.* {
    <fields>;
    void set*(***);
}
-keepclassmembers class com.zsh.qqbot.config.* {
    <fields>;
    void set*(***);
    *** get*();
}
# 保持目录, 注释该配置也没有打乱目录
#-keepdirectories

# 不优化代码, spring的项目优化代码会引入其他问题
-dontoptimize
# 禁止将类标记为 final
#-optimizations !class/marking/final

# 不混淆代码, 混淆代码会不在同目录下重新相同的bean(a, b)
#-dontobfuscate
# 打印混淆的映射关系
# printmapping "${buildDir}/mapping.txt"

# 混淆需要定义bean name, 否则会出现多个bean a
# 将所有类文件移动到指定包中重新打包（防止不同目录出现重复的bean）
-repackageclasses 'com.zsh.qqbot'
# 不模糊匹配属性文件
-useuniqueclassmembernames
-dontusemixedcaseclassnames
# 保留参数名称, 兼容属性和mapper, 下面配置对dao没有生效
#-keepparameternames
# 保留参数名称, 兼容属性和mapper
-keepattributes MethodParameters
# 注意, 不支持EnableCaching注解, 使用其查不到数据

# 不收缩代码, 收缩代码会删除所有未使用的类和类成员(spring相关会存在问题)
-dontshrink
# 打印收缩的文件
# -printusage "${buildDir}/useless.txt"

# 保留注解信息
-keepattributes *Annotation*

# 保留类及所有方法
#-keep public class * {
#    @org.springframework.stereotype.* *;
#    @org.springframework.web.bind.annotation.RestController *;
#    @org.springframework.context.annotation.* *;
#}
# 保留类指定方法或成员
#-keepclassmembers public class * {
#      @org.springframework.beans.factory.annotation.* *;
#      @org.springframework.context.annotation.* *;
#      @org.springframework.web.bind.annotation.* *;
#}

-verbose
