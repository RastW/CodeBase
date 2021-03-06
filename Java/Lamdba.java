// 1. 实现函数式接口
// Lamdba 表达式 可用于更简便的实现接口，但只限于函数式接口, 
// 注: 函数式接口代表，此接口除去default默认实现方法与系统默认重写方法（类似tostring），只实现单个接口
// 语法: 
// (参数) -> {
// 		方法体
// };
//
// 2. 函数饮用
// 饮用一个已经存在的方法，以此来代替要实现的函数的饮用
// 阅读此文件请根据main方法进行阅读，从上往下依次是从基础到深入的代码实现
public class Program {

	public static void main(String[] args) {
		// 无参数、无返回的函数式接口
		InterfaceImpl();

		// Lamdba语法基础
		LambdaBase();
		
		// lambda语法进阶
		LambdaPro();

		// lambda函数应用
		LambdaFunctionReference();
	}

	// 示例
	private static void InterfaceImpl() {
		// 使用显示的实现类对象
		SingleReturnSingleParameter parameter1 = new Impl();
		// 匿名内部类，隐式实现
		SingleReturnSingleParameter parameter2 = new SingleReturnSingleParameter() {
			@override
			public int test(int a) {
				return a * a;
			}
		}
		// Lamdba表达式
		SingleReturnSingleParameter parameter3 = a -> a * a;

		// 使用
		System.out.println(parameter1.test(10));
		System.out.println(parameter2.test(10));
		System.out.println(parameter3.test(10));
	}

	// Lamdba语法基础
	private static void LambdaUse() {
		// 1. lambda实现无返回无参数函数式接口
		NoneReturnNoneParameter lambd1 = () -> {
			System.out.println("hello, lambd");
		};
		lambd1.use();
		
		// 2. lambda实现无返回无参数函数式接口
		NoneReturnSingleParameter lambd2 = (int a) -> {
			System.out.println("hello, lambd2 parameter : " + a);
		};
		lambd2.use(10);

		// 3. lambda实现无返回值多参数函数式接口
		NoneReturnMutipleParameter lambd3 = (int a, int b) -> {
			System.out.println("hello,lambda a * b: " + a * b);
		};
		lambd3.use(10, 20);

		// 4. lambda实现有返回值无参数的函数式接口
		SingleReturnNoneParameter lambd4 = () -> {
			System.out.println("hello, lambda");
			return 10;
		};
		int lambd4return = lambd4.use();
		System.out.println(lambd4return);

		// 5. lambd5实现单参数单返回值式接口
		SingleReturnSingleParameter lambd5 = (int a) -> {
			System.out.println("hello, lambda");
			return a + 1;
		};
		int lambd5return = lambd5.use();
		System.out.println(lambd5return);
	}

	// lambda语法进阶
	private static void LambdaPro() {
		// 1. lambda实现单参数单返回值式接口
		// 1. 参数的类型在方法中已经定义，因此可以省略; 若要省略则必须全部省略
		// 2. 若参数类型有，且只有一个，小括号可以省略
		// 3. 若方法体有且只有一语句，可以省略大括号; 如果该语句为return语句，则续省略'return'关键字
		SingleReturnSingleParameter lambda1 = a -> a * a;

		// 2. lambda实现单返回多参数函数式接口
		SingleReturnMutipleParameter lambda2 = (a, b) -> a * b;
	}

	// 引用函数
	private static void LambdaFunctionReference() {
		// 使用lambda基础进行方法调用
		SingleReturnMutipleParameter lambda1 = (x, y) -> calculate(x, y);
		System.out.println(lambda1.use(10, 20));

		// 使用静态函数引用
		// 类名::静态方法名
		// 1. 函数引用引用的方法参数与返回值必须相同
		// 2. 方法名不加小括号
		SingleReturnMutipleParameter lambda2 = Program::calculate;
		System.out.println(lambda2.use(10, 20));

		// 使用非静态方法
		// 对象::非静态方法名
		Program program = new Program();
		SingleReturnMutipleParameter lambda3 = program::calculate2;
		SingleReturnMutipleParameter lambda4 = new Program()::calculate2;
		System.out.println(lambda3.use(10, 20) + lambda4.use(20, 10));

		// 使用引用函数 引用构造方法
		// 类::new
		GetPersonWithNoneParameter getPerson1 = Person::new;
		GetPersonWithMutipleParameter getPerson2 = Person::new;
		Person person = getPerson1.get();

		// 当使用一个对象作为参数，调用对象中的方法可以完成语句时，可以简写
		GetPersonName getPersonName = person -> person.getName();
		// 可以简写为:
		GetPersonName getPersonName = Person::getName;
	}



	private static void calculate(int x, int y) {
		if (x > y) {
			return x - y;
		} else if (x > x) {
			return y - x;
		}
		return x + y;
	}
 
	private int calculate2(int x, int y) {
		if (x > y) {
			return x - y;
		} else if (x > x) {
			return y - x;
		}
		return x + y;
	}

}


public static void Impl implements SingleReturnSingleParameter {

	@override
	public int test(int a) {
		return a * a;
	}
}



// ---------------------------------- 以下为多个Lamdba表达式函数式接口原型，可直接使用 --------------------------------------
// 无返回无参数
// 此注解可用于声明此接口为函数式接口，与 @override类似
@FunctionInterface
public interface NoneReturnNoneParameter {

	void use();
}


// 无返回单参数
@FunctionInterface
public interface NoneReturnSingleParameter {

	void use(int a);
}


// 无返回多参数
@FunctionInterface
public interface NoneReturnMutipleParameter {

	void use(int a, int b);
}

// 有返回无参数
@FunctionInterface
public interface SingleReturnNoneParameter {

	int use();
}


// 单返回单参数
@FunctionInterface
public interface SingleReturnSingleParameter {

	int use(int a);
}

// 单返回多参数
@FunctionInterface
public interface SingleReturnMutipleParameter {
	int use(int a, int b);
}

// ---------------------------------- Persion类, 用于展示lambda构造方法引用 --------------------------------------
public class Person {
	private String name;
	private int age;

	public Person() {
		Syste.out.println("Person 无参构造方法");
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
		System.out.println("Person 有参构造方法");
	}

	public String getName() {
		return this.name;
	}
}


// 无参构造
@FunctionInterface
public interface GetPersonWithNoneParameter {
	Person get();
}

// 有参构造
@FunctionInterface
public interface GetPersonWithMutipleParameter {
	Person get(String name, int age);
}

@FunctionInterface
public interface GetPersonName {
	String get();
}
