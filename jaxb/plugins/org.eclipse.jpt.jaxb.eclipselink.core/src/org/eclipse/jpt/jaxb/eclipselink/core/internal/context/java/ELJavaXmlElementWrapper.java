package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementWrapper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlElementWrapper
		extends GenericJavaXmlElementWrapper {
	
	public ELJavaXmlElementWrapper(JaxbAttributeMapping parent, Context context) {
		super(parent, context);
	}
	
	
	protected Context getContext() {
		return (Context) this.context;
	}
	
	@Override
	protected void validateQName(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (! getContext().hasXmlPath()) {
			super.validateQName(messages, reporter, astRoot);
		}
	}
	
	public interface Context
			extends GenericJavaXmlElementWrapper.Context {
		
		boolean hasXmlPath();
	}
}
