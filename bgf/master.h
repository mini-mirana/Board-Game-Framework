#pragma once
#include "jnipp.h"
class Master{
public:
	volatile static jni::Vm vm;
	static jni::Class Integer;
	static jni::Object i;
	static jni::Class Ball;
	static jni::method_t exe;
	static jni::Class ManageBoard;
	static jni::Object mB;
	static jni::method_t showMsg;
	static jni::method_t movePiece;

};

volatile jni::Vm Master::vm = *new jni::Vm("C:\\Program Files (x86)\\Java\\jre-9\\bin\\server\\jvm.dll");
jni::Class Master::Integer = jni::Class("java/lang/Integer");
jni::Object Master::i = Integer.newInstance("1000");
jni::Class Master::Ball = jni::Class("Ball");
jni::method_t Master::exe = Ball.getStaticMethod("exe", "(IIIIIIILjava/lang/String;)I");
jni::Class Master::ManageBoard = jni::Class("ManageBoard");
jni::Object Master::mB = ManageBoard.newInstance();
jni::method_t Master::movePiece = ManageBoard.getMethod("movePiece", "(IIII)V");
jni::method_t Master::showMsg = Ball.getStaticMethod("showMsg", "(Ljava/lang/String;)V");