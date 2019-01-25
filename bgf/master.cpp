#include "master.h"


volatile jni::Vm Master::vm = *new jni::Vm("C:\\Program Files (x86)\\Java\\jre-9\\bin\\server\\jvm.dll");
jni::Class Master::Integer = jni::Class("java/lang/Integer");
jni::Object Master::i = Integer.newInstance("1000");
jni::Class Master::Ball = jni::Class("Ball");
jni::method_t Master::exe = Ball.getStaticMethod("exe", "(IIIIIIILjava/lang/String;)I");

jni::Class Master::ManageBoard = jni::Class("ManageBoard");
jni::Object Master::mB = ManageBoard.newInstance();
jni::method_t Master::movePiece = ManageBoard.getMethod("movePiece", "(IIII)V");
jni::method_t Master::showMsg = Ball.getStaticMethod("showMsg", "(Ljava/lang/String;)V");

jni::field_t Master::moveQueue = Master::ManageBoard.getStaticField("moveQueue", "Ljava/lang/String;");
jni::field_t Master::gameStarted = Master::ManageBoard.getStaticField("gameStarted", "Z");
jni::field_t Master::queueTriggered = Master::ManageBoard.getStaticField("queueTriggered", "Z");
jni::field_t Master::answerQueue = Master::ManageBoard.getStaticField("answerQueue", "Z");
