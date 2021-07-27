package br.com.obpc.representations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Representation {
	
	private Decorator _link;
	
	public Representation() {}
	
	public Representation(HttpServletRequest request, String uriBase) {
		this._link = new Decorator(createLink(request, uriBase));
	}

	public Decorator get_link() {
		return _link;
	}

	public void set_link(Decorator _link) {
		this._link = _link;
	}
	
	private String createLink(HttpServletRequest request, String uriBase) {
		return request.getRequestURL().toString().split(request.getRequestURI())[0]+uriBase;
	}
	
	public static <T> List<T> getListRepresentation(List<?> objectList, HttpServletRequest request, Class<T> typeTarget) throws NoSuchMethodException, SecurityException{
		List<T> representationList = new ArrayList<>();		
		
		if(!objectList.isEmpty()) {
			Constructor<T> constructor = typeTarget.getConstructor(objectList.get(0).getClass(), HttpServletRequest.class);
			objectList.forEach(item -> {			
					try {
						representationList.add(constructor.newInstance(item, request));
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}		
			});
		}
		
		return representationList;
	}


}
