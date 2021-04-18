package dataframe;

import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.lang.Class;

public class Serie<T> implements Serial<T>{

	private List<T> elements;
	private boolean isNum;

	private String class_name;


	public Serie() {
		elements = new ArrayList<T>();
		isNum = false;	
	}

	public boolean add(T e){
		//TODO : add compatibility with heredity
		if(elements.size()==0){
			if(e instanceof Integer || e instanceof Float || e instanceof Double){
				isNum = true;
				elements.add(e);

			}else if(e instanceof String){
				elements.add(e);

			}else{
				throw new IllegalArgumentException("Ce type n'est pas pris en charge");
			}

			class_name = e.getClass().getName();

		}else{
			String test = e.getClass().getName();

			if(test.equals(class_name)){
				elements.add(e);
				return true;
			}
		}
		return false;
	}


    public boolean remove(int index){
    	if(index < 0 || index >= elements.size())
    		throw new NullPointerException("L'index est incorrect");
    	if(index >= 0 && index < elements.size()){
    		elements.remove(index);
    		return true;
    	}
    	return false;
    }

    public int size(){
    	return elements.size();
    }

    public String toString(){
    	return ""+elements;
    }

    public void print(){
    	System.out.println(this);
    }

    public boolean set(T e, int index){
    	String test = e.getClass().getName();
    	if(index < 0 || index >= elements.size())
    		throw new NullPointerException("L'index est incorrect");
    	if(test.equals(class_name)){
	    	elements.remove(index);
	    	elements.add(index,e);
	    	return true;
    	}
    	return false;
    }


    public T get(int index){
    	T r = null;
    	if(index < 0 || index >= elements.size())
    		throw new NullPointerException("L'index est incorrect");
    	if(index >= 0 && index < elements.size())
    		r = elements.get(index);
    	return r;
    }

}