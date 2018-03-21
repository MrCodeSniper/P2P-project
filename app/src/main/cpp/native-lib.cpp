#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_mac_back_ui_activity_FirstInActivity_stringFromJNI(JNIEnv *env, jobject instance) {

    // TODO
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}




//extern "C"
//JNIEXPORT jint JNICALL
//Java_com_example_mac_back_utils_PatchUtils_patch(JNIEnv *env, jclass type, jstring oldApkPath_,
//                                                 jstring newApkPath_, jstring patchPath_) {
//    const char *oldApkPath = env->GetStringUTFChars(oldApkPath_, 0);
//    const char *newApkPath = env->GetStringUTFChars(newApkPath_, 0);
//    const char *patchPath = env->GetStringUTFChars(patchPath_, 0);
//
//    // TODO
//
//    env->ReleaseStringUTFChars(oldApkPath_, oldApkPath);
//    env->ReleaseStringUTFChars(newApkPath_, newApkPath);
//    env->ReleaseStringUTFChars(patchPath_, patchPath);
//}