package br.com.obpc.representations;

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

}
