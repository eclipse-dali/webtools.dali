package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IPersistentType;

public class DefaultsContextWrapper implements DefaultsContext
{
	private DefaultsContext wrappedDefaultsContext;
	
	public DefaultsContextWrapper(DefaultsContext wrappedDefaultsContext) {
		this.wrappedDefaultsContext = wrappedDefaultsContext;
	}
	public CompilationUnit astRoot() {
		return this.wrappedDefaultsContext.astRoot();
	}

	public Object getDefault(String key) {
		return this.wrappedDefaultsContext.getDefault(key);
	}

	public IPersistentType persistentType(String fullyQualifiedTypeName) {
		return this.wrappedDefaultsContext.persistentType(fullyQualifiedTypeName);
	}
	
	protected DefaultsContext getWrappedDefaultsContext() {
		return this.wrappedDefaultsContext;
	}
}
