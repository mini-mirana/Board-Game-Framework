#include "jnipp.h"
#include <iostream>
#include <cstdlib>

int main() {
	// An instance of the Java VM needs to be created.
	jni::Vm vm("C:\\Program Files (x86)\\Java\\jre-9\\bin\\server\\jvm.dll");
	//jni::Vm vm("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\server\\jvm.dll");
	//jni::Vm vm("C:\\Program Files (x86)\\Java\\jre7\\bin\\client\\jvm.dll");

	// Create an instance of java.lang.Integer
	jni::Class Integer = jni::Class("java/lang/Integer");
	jni::Object i = Integer.newInstance("1000");

	// Call the `toString` method on that integer
	std::string str = i.call<std::string>("toString");
	std::cout << str << std::endl;
	system("pause");
	
	/*jni::Class MyTest = jni::Class("MyTest");
	jni::method_t mm = MyTest.getStaticMethod("mymain","()I");
	int z = MyTest.call<int>(mm);
	std::cout << z << std::endl;
	system("pause");*/

	/*jni::Class Main = jni::Class("Main");
	jni::method_t run = Main.getStaticMethod("run", "()I");
	int c = Main.call<int>(run);
	system("pause");*/

	jni::Class Ball = jni::Class("Ball");
	jni::method_t exe = Ball.getStaticMethod("exe", "(IIIIIIILjava/lang/String;)I");
	int e = Ball.call<int>(exe, 2, 1, 0, 0, 8, 8, 16, "");
	system("pause");

	// The Java VM is automatically destroyed when it goes out of scope.
	return 0;
}