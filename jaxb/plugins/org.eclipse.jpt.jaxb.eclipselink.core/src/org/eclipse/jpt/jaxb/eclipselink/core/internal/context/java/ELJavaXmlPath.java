package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
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
	
	protected List<Step> steps;
	
	
	public ELJavaXmlPath(JavaContextNode parent, Context context) {
		super(parent);
		this.context = context;
		initValue();
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
		initSteps();
	}
	
	protected void syncValue() {
		setValue_(getAnnotation().getValue());
		syncSteps();
		
	}
	
	protected XmlPathAnnotation getAnnotation() {
		return this.context.getAnnotation();
	}
	
	
	// ***** steps *****
	
	protected void initSteps() {
		this.steps = new Vector<Step>();
		CollectionTools.addAll(this.steps, parse(this.value));
	}
	
	protected void syncSteps() {
		String oldXpath = getCompleteXpath();
		if (! StringTools.stringsAreEqual(this.value, oldXpath)) {
			this.steps.clear();
			CollectionTools.addAll(this.steps, parse(this.value));
		}
	}
	
	protected String getCompleteXpath() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<Step> stream = this.steps.iterator(); stream.hasNext(); ) {
			sb.append(stream.next().value);
			if (stream.hasNext()) {
				sb.append(DELIM);
			}
		}
		return sb.toString();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAnnotation().getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		String completeXpath = getCompleteXpath();
		if (completeXpath.startsWith(DELIM)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XML_PATH__ROOT_NOT_SUPPORTED,
								new String[] { this.value },
								ELJavaXmlPath.this,
								getValueTextRange(astRoot)));
			return;
		}
		
		IMessage firstFormError = null;
		Iterator<Step> stream = this.steps.iterator();
		while (firstFormError == null && stream.hasNext() ) {
			firstFormError = stream.next().getFirstFormError(messages, astRoot);
		}
		if (firstFormError != null) {
			messages.add(firstFormError);
			return;
		}
	}
	
	protected TextRange getValueTextRange(CompilationUnit astRoot) {
		// should never be null
		return getAnnotation().getValueTextRange(astRoot);
	}
	
	protected TextRange buildTextRange(Step step, CompilationUnit astRoot) {
		TextRange entireTextRange = getValueTextRange(astRoot);
		int start = 1; // move to right one since initial text range includes first quote
		for (Step each : this.steps) {
			int length = each.value.length();
				if (each == step) {
				if (length == 0) {
					if (this.steps.size() > 1) {
						// expand text range to include either leading or trailing "/"
						length += 1;
						if (step.index != 0) {
							// if step is not first step, use leading "/"
							start --;
						}
					}
					else {
						return entireTextRange;
					}
				}
				return new SimpleTextRange(entireTextRange.getOffset() + start, length, entireTextRange.getLineNumber());
			}
			else {
				start += length + 1; // step plus "/"
			}
		}
		throw new IllegalArgumentException("Step must be in list of this XmlPath's steps.");
	}
	
	
	// ***** xpath parsing *****
	
	protected static String DELIM = "/";
	
	protected static String TEXT = "text()";
	
	protected static String SELF = ".";
	
	protected static String ATT_PREFIX = "@";
	
	protected Step[] parse(String xpath) {
		if (xpath == null) {
			return new Step[0];
		}
		String[] segments = xpath.split(DELIM, -1);
		Step[] steps = new Step[segments.length];
		for (int i = 0; i < segments.length; i ++ ) {
			steps[i] = new Step(segments[i], i);
		}
		return steps;
	}
	
	
	public interface Context {
		
		XmlPathAnnotation getAnnotation();
	}
	
	
	protected class Step {
		
		protected String value;
		
		protected int index;
		
		protected Flavor flavor;
		
		
		protected Step(String value, int index) {
			this.value = value;
			this.index = index;
			this.flavor = determineFlavor();
		}
		
		protected Flavor determineFlavor() {
			if (StringTools.stringIsEmpty(this.value)) {
				return Flavor.EMPTY;
			}
			else if (TEXT.equals(this.value)) {
				return Flavor.TEXT;
			}
			else if (SELF.equals(this.value)) {
				return Flavor.SELF;
			}
			else if (this.value.startsWith(ATT_PREFIX)) {
				return Flavor.ATTRIBUTE;
			}
			else {
				return Flavor.ELEMENT;
			}
		}
		
		protected IMessage getFirstFormError(List<IMessage> messages, CompilationUnit astRoot) {
			switch (this.flavor) {
				case EMPTY :
					return ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_PATH__INVALID_FORM_ILLEGAL_SEGMENT,
							new String[] { this.value },
							ELJavaXmlPath.this,
							ELJavaXmlPath.this.buildTextRange(this, astRoot));
				case SELF :
					if (this.index != 0) {
						return ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_PATH__SELF_SEGMENT_MUST_BE_FIRST_SEGMENT,
							ELJavaXmlPath.this,
							ELJavaXmlPath.this.buildTextRange(this, astRoot));
					}
					return null;
				case TEXT :
					if (this.index != ELJavaXmlPath.this.steps.size() - 1) {
						return ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_PATH__TEXT_SEGMENT_MUST_BE_LAST_SEGMENT,
							ELJavaXmlPath.this,
							ELJavaXmlPath.this.buildTextRange(this, astRoot));
					}
					return null;
				case ATTRIBUTE :
					if (this.index != ELJavaXmlPath.this.steps.size() - 1) {
						return ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XML_PATH__ATTRIBUTE_SEGMENT_MUST_BE_LAST_SEGMENT,
							ELJavaXmlPath.this,
							ELJavaXmlPath.this.buildTextRange(this, astRoot));
					}

				default :
					return null;
			}
		}
	}
	
	
	protected enum Flavor {
		
		EMPTY,
		
		SELF,
		
		TEXT,
		
		ELEMENT,
		
		ATTRIBUTE
	}
}
