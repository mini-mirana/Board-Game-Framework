#include "jnipp.h"
#include <cstdlib>
#include <iostream>

int main()
{
	// An instance of the Java VM needs to be created.
	jni::Vm vm("C:\\Program Files\\Java\\jre-9\\bin\\server\\jvm.dll");

	// Create an instance of java.lang.Integer
	jni::Class Integer = jni::Class("java/lang/Integer");
	jni::Object i = Integer.newInstance("1000");

	// Call the `toString` method on that integer
	std::string str = i.call<std::string>("toString");
	std::cout << str << std::endl;
	system("pause");
	// The Java VM is automatically destroyed when it goes out of scope.
	return 0;
}