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

	static jni::field_t gameStarted;
	static jni::field_t moveQueue;
	static jni::field_t queueTriggered;
	static jni::field_t answerQueue;
};
