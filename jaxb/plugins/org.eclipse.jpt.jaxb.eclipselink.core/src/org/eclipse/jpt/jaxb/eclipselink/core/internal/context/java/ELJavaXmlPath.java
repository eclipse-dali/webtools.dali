package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPathFactory;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlPath
		extends AbstractJavaContextNode
		implements ELXmlPath {
		
	protected String value;
	
	protected Context context;
	
	
	public ELJavaXmlPath(JavaContextNode parent, Context context) {
		super(parent);
		this.context = context;
		initValue();
	}
	
	
	protected JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbType().getJaxbPackage();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncValue();
	}
	
	
	// ***** value *****
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		getAnnotation().setValue(value);
		setValue_(value);
	}
	
	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		firePropertyChanged(VALUE_PROPERTY, old, this.value);
	}
	
	protected void initValue() {
		this.value = getAnnotation().getValue();
	}
	
	protected void syncValue() {
		setValue_(getAnnotation().getValue());
	}
	
	protected XmlPathAnnotation getAnnotation() {
		return this.context.getAnnotation();
	}
	
	protected JaxbAttributeMapping getAttributeMapping() {
		return this.context.getAttributeMapping();
	}
	
	protected JaxbClassMapping getClassMapping() {
		return getAttributeMapping().getClassMapping();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		if (getAnnotation().valueTouches(pos, astRoot) && this.value != null) {
			XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
			XPath xpath = XPathFactory.instance().getXpath(this.value);
			return xpath.getCompletionProposals(new XPathContext(astRoot), xsdType, pos, filter);
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAnnotation().getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (StringTools.stringIsEmpty(this.value)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XML_PATH__NOT_SPECIFIED,
								ELJavaXmlPath.this,
								getValueTextRange(astRoot)));
			return;
		}
		
		if (this.value.startsWith(XPath.DELIM)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__ROOT_NOT_SUPPORTED,
								ELJavaXmlPath.this,
								getValueTextRange(astRoot)));
			return;
		}
		
		XsdTypeDefinition xsdType = getClassMapping().getXsdTypeDefinition();
		XPath xpath = XPathFactory.instance().getXpath(this.value);
		xpath.validate(new XPathContext(astRoot), xsdType, messages);
	}
	
	protected TextRange getValueTextRange(CompilationUnit astRoot) {
		// should never be null
		return getAnnotation().getValueTextRange(astRoot);
	}
	
	
	public interface Context {
		
		XmlPathAnnotation getAnnotation();
		
		JaxbAttributeMapping getAttributeMapping();
	}
	
	
	protected class XPathContext
		implements XPath.Context {
		
		private CompilationUnit astRoot;
		
		protected XPathContext(CompilationUnit astRoot) {
			this.astRoot = astRoot;
		}
		
		
		public JaxbNode getContextObject() {
			return ELJavaXmlPath.this;
		}
		
		public JaxbPackage getJaxbPackage() {
			return ELJavaXmlPath.this.getJaxbPackage();
		}
		
		public TextRange getTextRange() {
			return ELJavaXmlPath.this.getValueTextRange(this.astRoot);
		}
	}
}
