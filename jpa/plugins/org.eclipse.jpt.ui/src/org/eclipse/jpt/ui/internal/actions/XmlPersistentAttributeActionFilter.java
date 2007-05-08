package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.ui.IActionFilter;

public class XmlPersistentAttributeActionFilter 
	extends PersistentAttributeActionFilter
{
	public static final String IS_VIRTUAL = "isVirtual";
	
	
	public boolean testAttribute(Object target, String name, String value) {
		if (! IS_VIRTUAL.equals(name)) {
			return super.testAttribute(target, name, value);
		}
		
		Boolean booleanValue;
		if ("true".equals(value)) {
			booleanValue = true;
		}
		else if ("false".equals(value)) {
			booleanValue = false;
		}
		else {
			return false;
		}
		return ((XmlPersistentAttribute) target).isVirtual() == booleanValue;
	}
	
	
	public static final class Factory
		implements IAdapterFactory
	{
		private static final Class[] ADAPTER_TYPES = { IActionFilter.class };
		
		public Object getAdapter(final Object adaptable, final Class adapterType ) {
			if( adapterType == IActionFilter.class ) {
				return new XmlPersistentAttributeActionFilter();
			} else {
				return null;
			}
		}
	    
		public Class[] getAdapterList() {
			return ADAPTER_TYPES;
		}
	}
}
