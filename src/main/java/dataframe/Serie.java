package dataframe;

import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.lang.Class;

public class Serie<T> implements Serial<T>{

	private List<T> elements;


	public Serie() {
		elements = new ArrayList<T>();
	
	}

	public boolean add(T e){
		//TODO : add compatibility with heredity
		if(elements.size()==0){
			if(e instanceof Integer || e instanceof Float || e instanceof Double || e instanceof String){
			
				elements.add(e);

			}else{
				throw new IllegalArgumentException("Invalid type element");
			}
		}else{
			elements.add(e);
			return true;
			
		}
		return false;
	}


    public boolean remove(int index){
        T e = elements.remove(index);
        return e != null;
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
    	if(index < 0 || index >= elements.size())
    		throw new NullPointerException("Invalid index");
    	
	    elements.remove(index);
	    elements.add(index,e);
	    return true;
    }


    public T get(int index){
    	return elements.get(index);
    }

}