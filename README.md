先看接入步骤：
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```java
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```java
    dependencies {
	        implementation 'com.github.linpeixu:GeneralDialog:1.0.1'
            //或者implementation 'com.gitlab.linpeixu:GeneralDialog:1.0.1'
	}
```

使用方法示例如下

```java
new GeneralDialog.Builder()
                            .layoutId(R.layout.xxx)
                            .theme(R.style.GeneralDialog)
                            .width(xxx)
                            .height(xxx)
                            .gravity(Gravity.CENTER)
                            .onInitListener(new GeneralDialog.OnInitListener() {
                                @Override
                                public void onInit(DialogInterface dialog, View contentView) {

                                }
                            })
                            .build(this)
                            .show();
```

```java
//假设UserInfoItemBinding为弹窗的ContentView对应的ViewDataBinding类
new GeneralDialog.BindingBuilder<UserInfoItemBinding>()
                            .viewDataBinding(new GeneralDialog.ViewDataBinding<UserInfoItemBinding>(UserInfoItemBinding.inflate(getLayoutInflater())) {
                                @Override
                                protected View getRoot() {
                                    return viewDataBinding.getRoot();
                                }
                            })
                            .theme(R.style.GeneralDialog)
                            .width(xxx)
                            .height(xxx)
                            .gravity(Gravity.CENTER)
                            .onInitListener(new GeneralDialog.OnBindingInitListener<UserInfoItemBinding>() {
                                @Override
                                public void onInit(DialogInterface dialog, UserInfoItemBinding viewDataBinding) {

                                }
                            })
                            .build(this)
                            .show();
```
