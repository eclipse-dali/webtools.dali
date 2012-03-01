package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlDiscriminatorNode;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java.XPathFactory;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaXmlDiscriminatorNode
		extends AbstractJavaContextNode
		implements ELXmlDiscriminatorNode {
	
	protected String value;
	
	
	public ELJavaXmlDiscriminatorNode(ELJavaClassMapping parent) {
		super(parent);
		initValue();
	}
	
	
	protected ELJavaClassMapping getClassMapping() {
		return (ELJavaClassMapping) getParent();
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
	
	protected XmlDiscriminatorNodeAnnotation getAnnotation() {
		return getClassMapping().getXmlDiscriminatorNodeAnnotation();
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
								ELJavaXmlDiscriminatorNode.this,
								getValueTextRange(astRoot)));
			return;
		}
		
		if (this.value.startsWith(XPath.DELIM)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XML_PATH__ROOT_NOT_SUPPORTED,
								ELJavaXmlDiscriminatorNode.this,
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
	
	
	protected class XPathContext
		implements XPath.Context {
		
		private CompilationUnit astRoot;
		
		protected XPathContext(CompilationUnit astRoot) {
			this.astRoot = astRoot;
		}
		
		
		public JaxbNode getContextObject() {
			return ELJavaXmlDiscriminatorNode.this;
		}
		
		public JaxbPackage getJaxbPackage() {
			return ELJavaXmlDiscriminatorNode.this.getJaxbPackage();
		}
		
		public TextRange getTextRange() {
			return ELJavaXmlDiscriminatorNode.this.getValueTextRange(this.astRoot);
		}
	}
}
