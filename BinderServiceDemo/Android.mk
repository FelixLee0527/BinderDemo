LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_USE_AAPT2 := true

LOCAL_PACKAGE_NAME := BinderServiceDemo
LOCAL_CERTIFICATE := platform
LOCAL_PRIVILEGED_MODULE := true
LOCAL_USE_AAPT2 := true
LOCAL_DEX_PREOPT := false

src_dirs :=  app/src/main/java
aidl_dirs :=  app/src/main/aidl
res_dirs :=  app/src/main/res

LOCAL_SRC_FILES := $(call all-java-files-under, $(src_dirs))
LOCAL_RESOURCE_DIR := $(addprefix $(LOCAL_PATH)/, $(res_dirs))
LOCAL_SRC_FILES += $(call all-Iaidl-files-under,$(aidl_dirs))
LOCAL_AIDL_INCLUDES += $(LOCAL_PATH)/$(aidl_dirs)

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml

LOCAL_SHARED_ANDROID_LIBRARIES := \
    androidx.appcompat_appcompat

LOCAL_PRIVATE_PLATFORM_APIS :=true
LOCAL_PROGUARD_ENABLED := full
LOCAL_PROGUARD_FLAG_FILES := proguard.flags

ifneq ($(INCREMENTAL_BUILDS),)
    LOCAL_JACK_ENABLED := incremental
    LOCAL_JACK_FLAGS := --multi-dex native
endif

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \

include $(BUILD_MULTI_PREBUILT)

# For the test package.
include $(call all-makefiles-under, $(LOCAL_PATH))
