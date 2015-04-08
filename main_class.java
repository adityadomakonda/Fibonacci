import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

public class main_class{
	public static void main(String[] args){
		System.out.println("Calling other class");
		out_class.test_func();
		System.out.println("Called other class");
	}
}