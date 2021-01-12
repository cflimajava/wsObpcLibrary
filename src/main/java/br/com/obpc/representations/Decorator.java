package br.com.obpc.representations;

/**
 * This class is used to create the decorator 
 * for resources following HATEOAS practices 
 * 
 * @author Cristiano F. Lima
 *
 */
public class Decorator {

	private Self self;
	
	public Decorator(String href) {
		this.self = new Self(href);
	}
	
	public Self getSelf() {
		return self;
	}

	public void setSelf(Self self) {
		this.self = self;
	}

	public class Self{
		
		private String href;

		public Self(String href) {
			this.href = href;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}
		
		
	}
}
