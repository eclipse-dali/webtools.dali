/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.xpath.java;

import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdAttributeUse;
import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class XPath {
	
	public static String DELIM = "/";
	
	public static String ATT_PREFIX = "@";
	
	public static String TEXT = "text()";
	
	public static String SELF = ".";
	
	public static String COLON = ":";
	
	public static char OPEN_BRACKET = '[';
	
	public static char CLOSE_BRACKET = ']';
	
	
	public static String attributeXPath(String prefix, String localName) {
		if (prefix == null) {
			return StringTools.concatenate(ATT_PREFIX, localName);
		}
		return StringTools.concatenate(ATT_PREFIX, prefix, COLON, localName);
	}
	
	public static String elementXPath(String prefix, String localName) {
		if (prefix == null) {
			return localName;
		}
		return StringTools.concatenate(prefix, COLON, localName);
	}
	
	
	List<Step> steps;
	
	
	XPath(String xpathString) {
		this.steps = new Vector<Step>();
		parse(xpathString);
	}
	
	
	protected int getIndex(Step step) {
		return this.steps.indexOf(step);
	}
	
	protected Step getFirstStep() {
		if (this.steps.size() > 0) {
			return this.steps.get(0);
		}
		return null;
	}
	
	protected Step getNextStep(Step step) {
		int nextIndex = getIndex(step) + 1;
		if (this.steps.size() > nextIndex) {
			return this.steps.get(nextIndex);
		}
		return null;
	}
	
	protected void parse(String xpath) {
		for (String segment : xpath.split(DELIM, -1)) {
			this.steps.add(createStep(segment));
		}
	}
	
	protected Step createStep(String stepValue) {
		if (TEXT.equals(stepValue)) {
			return new TextStep();
		}
		else if (SELF.equals(stepValue)) {
			return new SelfStep();
		}
		else if (stepValue.startsWith(ATT_PREFIX)) {
			return tryCreateAttributeStep(stepValue);
		}
		else {
			return tryCreateElementStep(stepValue);
		}
	}
	
	protected Step tryCreateAttributeStep(String stepValue) {
		// must start with "@" - already tested that
		try {
			String attributeStepValue = stepValue.substring(1);
			String[] stepParts = parseStepParts(attributeStepValue);
			return new AttributeStep(stepParts[0], stepParts[1], ArrayTools.subArray(stepParts, 2, stepParts.length));
		}
		catch (IllegalArgumentException ife) {
			return new InvalidStep(stepValue);
		}
	}
	
	protected Step tryCreateElementStep(String stepValue) {
		try {
			String[] stepParts = parseStepParts(stepValue);
			return new ElementStep(stepParts[0], stepParts[1], ArrayTools.subArray(stepParts, 2, stepParts.length));
		}
		catch (IllegalArgumentException ife) {
			return new InvalidStep(stepValue);
		}
	}
	
	protected String[] parseStepParts(String stepValue) {
		String[] predicate = new String[0];
		int predicateStart = stepValue.lastIndexOf(OPEN_BRACKET);
		while (predicateStart >= 0) {
			int predicateEnd = stepValue.lastIndexOf(CLOSE_BRACKET);
			if (predicateEnd <= predicateStart || predicateEnd != stepValue.length() - 1) {
				throw new IllegalArgumentException();
			}
			predicate = ArrayTools.add(predicate, 0, stepValue.substring(predicateStart +1, predicateEnd));
			stepValue = stepValue.substring(0, predicateStart);
			predicateStart = stepValue.lastIndexOf(OPEN_BRACKET);
		}
		
		String nsPrefix = null;
		int colon = stepValue.indexOf(COLON);
		if (colon > 0) {
			if (stepValue.indexOf(COLON, colon + 1) != -1) {
				throw new IllegalArgumentException();
			}
			nsPrefix = stepValue.substring(0, colon);
			stepValue = stepValue.substring(colon +1);
		}
		
		if (StringTools.isBlank(stepValue)
				|| stepValue.indexOf(COLON) >= 0 
				|| stepValue.indexOf(OPEN_BRACKET) >= 0 
				|| stepValue.indexOf(CLOSE_BRACKET) >= 0
				|| stepValue.indexOf(' ') >= 0) {
			throw new IllegalArgumentException();
		}
		
		String[] result = ArrayTools.add(new String[0], nsPrefix);
		result = ArrayTools.add(result, stepValue);
		result = ArrayTools.addAll(result, predicate);
		return result;
	}
	
	
	// ***** content assist *****
	
	public Iterable<String> getCompletionProposals(
			Context context, XsdTypeDefinition xsdType, int pos) {
		
		return getFirstStep().getCompletionProposals(context, xsdType, StringTools.EMPTY_STRING, pos);
	}
	
	
	// ***** validation *****
	
	public void validate(Context context, XsdTypeDefinition xsdType, List<IMessage> messages) {
		getFirstStep().validate(context, xsdType, messages);
	}
	
	protected TextRange buildTextRange(Context context, Step step) {
		TextRange entireTextRange = context.getTextRange();
		int start = 0;
		for (Step each : this.steps) {
			int length = each.getValue().length() + 2;  // include leading/trailing " or /
			if (each == step) {
				return new SimpleTextRange(entireTextRange.getOffset() + start, length, entireTextRange.getLineNumber());
			}
			else {
				start += length - 1; // only include leading " or /
			}
		}
		throw new IllegalArgumentException("Step must be in list of this XPath's steps.");
	}
	
	
	protected class AttributeStep
			extends NamedComponentStep {
		
		protected AttributeStep(String nsPrefix, String localName, String[] predicates) {
			super(nsPrefix, localName, predicates);
		}
		
		
		@Override
		protected String getValue() {
			return ATT_PREFIX + super.getValue();
		}
		
		@Override
		protected XsdTypeDefinition resolveNextType(Context context, XsdTypeDefinition previousType) {
			String namespace = resolveNamespace(context);
			if (namespace == null) {
				return null;
			}
			
			XsdAttributeUse xsdAttribute = previousType.getAttribute(namespace, this.localName);
			return (xsdAttribute == null) ? null : xsdAttribute.getAttributeDeclaration().getType();
		}
		
		@Override
		protected void validate(
				Context context, XsdTypeDefinition previousType, List<IMessage> messages) {
			
			if (getNextStep() != null) {
				messages.add(
						ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__ATTRIBUTE_SEGMENT_MUST_BE_LAST_SEGMENT,
								context.getContextObject(),
								getTextRange(context)));
				return;
			}
			
			super.validate(context, previousType, messages);
		}
		
		@Override
		protected XsdTypeDefinition validateLocalName(
				Context context, XsdTypeDefinition previousType, String namespace, List<IMessage> messages) {
			
			XsdAttributeUse xsdAttribute= previousType.getAttribute(namespace, this.localName);
			if (xsdAttribute == null) {
				messages.add(
						ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__UNRESOLVED_ATTRIBUTE,
								new String[] { namespace, this.localName },
								context.getContextObject(),
								getTextRange(context)));
				return null;
			}
			else {
				return xsdAttribute.getAttributeDeclaration().getType();
			}
		}
		
//		@Override
//		protected TextRange buildNamespacePrefixTextRange(CompilationUnit astRoot) {
//			// assume nsPrefix != null
//			TextRange stepTextRange = ELJavaXmlPath.this.buildTextRange(this, astRoot);
//			return new SimpleTextRange(stepTextRange.getOffset() + 1, this.nsPrefix.length(), stepTextRange.getLineNumber());
//		}
	}

	protected class ElementStep
			extends NamedComponentStep {
		
		protected ElementStep(String nsPrefix, String localName, String[] predicates) {
			super(nsPrefix, localName, predicates);
		}
		
		
		@Override
		protected XsdTypeDefinition
				resolveNextType(Context context, XsdTypeDefinition previousType) {
		
			String namespace = resolveNamespace(context);
			if (namespace == null) {
				return null;
			}
			
			XsdElementDeclaration xsdElement = previousType.getElement(namespace, this.localName);
			return (xsdElement == null) ? null : xsdElement.getType();
		}
		
		
		@Override
		protected XsdTypeDefinition validateLocalName(
				Context context, XsdTypeDefinition previousType, String namespace, List<IMessage> messages) {
			
			XsdElementDeclaration xsdElement = previousType.getElement(namespace, this.localName);
			if (xsdElement == null) {
				messages.add(
						ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__UNRESOLVED_ELEMENT,
								new String[] { namespace, this.localName },
								context.getContextObject(),
								getTextRange(context)));
				return null;
			}
			else {
				return xsdElement.getType();
			}
		}
		
//		@Override
//		protected TextRange buildNamespacePrefixTextRange(CompilationUnit astRoot) {
//			// assume nsPrefix != null
//			TextRange stepTextRange = ELJavaXmlPath.this.buildTextRange(this, astRoot);
//			return new SimpleTextRange(stepTextRange.getOffset(), this.nsPrefix.length(), stepTextRange.getLineNumber());
//		}
	}

	protected class InvalidStep
			extends Step {
		
		protected String value;
		
		protected InvalidStep(String value) {
			super();
			this.value = value;
		}
		
		@Override
		protected String getValue() {
			return this.value;
		}
		
		@Override
		protected XsdTypeDefinition
				resolveNextType(Context context, XsdTypeDefinition previousType) {
			
			return null;
		}
		
		@Override
		protected void validate(
				Context context, XsdTypeDefinition previousType, List<IMessage> messages) {
			
			messages. add(ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.XPATH__INVALID_FORM_ILLEGAL_SEGMENT,
							new String[] { getValue() },
							context.getContextObject(),
							getTextRange(context)));
			return;
		}
	}

	protected abstract class NamedComponentStep
			extends Step {
		
		protected String nsPrefix;
		
		protected String localName;
		
		protected List<String> predicates;
		
		
		protected NamedComponentStep(String nsPrefix, String localName, String[] predicates) {
			super();
			this.nsPrefix = nsPrefix;
			this.localName = localName;
			this.predicates = new Vector<String>();
			CollectionTools.addAll(this.predicates, predicates);
		}
		
		@Override
		protected String getValue() {
			StringBuffer sb = new StringBuffer();
			if (this.nsPrefix != null) {
				sb.append(nsPrefix);
				sb.append(COLON);
			}
			sb.append(this.localName);
			for (String predicate : this.predicates) {
				sb.append(OPEN_BRACKET);
				sb.append(predicate);
				sb.append(CLOSE_BRACKET);
			}
			return sb.toString();
		}
		
		protected String resolveNamespace(Context context) {
			JaxbPackage pkg = context.getJaxbPackage();
			if (this.nsPrefix == null) {
				return "";
			}
			else {
				JaxbPackageInfo pkgInfo = pkg.getPackageInfo();
				return (pkgInfo == null) ? null : pkgInfo.getNamespaceForPrefix(this.nsPrefix);
			}
		}
		
		@Override
		protected void validate(
				Context context, XsdTypeDefinition previousType, List<IMessage> messages) {
			
			String namespace = resolveNamespace(context);
			
			if (namespace == null) {
				messages.add(
						ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__INVALID_NS_PREFIX,
								new String[] { this.nsPrefix },
								context.getContextObject(),
								getTextRange(context)));
			}
			
			XsdTypeDefinition nextType = (previousType == null) ? null : validateLocalName(context, previousType, namespace, messages);
			if (nextType != null) {
				Step nextStep = getNextStep();
				if (nextStep != null) {
					nextStep.validate(context, nextType, messages);
				}
			}
		}
		
		protected abstract XsdTypeDefinition validateLocalName(
				Context context, XsdTypeDefinition previousType, String namespace, List<IMessage> messages);
		
//		protected abstract TextRange buildNamespacePrefixTextRange(CompilationUnit astRoot);
	}

	protected class SelfStep
			extends Step {
		
		protected SelfStep() {
			super();
		}
		
		
		@Override
		protected String getValue() {
			return SELF;
		}
		
		@Override
		protected XsdTypeDefinition
				resolveNextType(Context context, XsdTypeDefinition previousType) {
			
			return previousType;
		}
		
		@Override
		protected void validate(
				Context context, XsdTypeDefinition previousType, List<IMessage> messages) {
			
			if (getIndex() != 0) {
				messages.add(
						ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__SELF_SEGMENT_MUST_BE_FIRST_SEGMENT,
								context.getContextObject(),
								getTextRange(context)));
				return;
			}
			
			if (getNextStep() != null) {
				getNextStep().validate(context, previousType, messages);
			}
		}
	}

	protected abstract class Step {
		
		protected int getIndex() {
			return XPath.this.getIndex(this);
		}
		
		protected Step getNextStep() {
			return XPath.this.getNextStep(this);
		}
		
		protected abstract String getValue();
		
		protected abstract XsdTypeDefinition resolveNextType(Context context, XsdTypeDefinition previousType);
		
		protected Iterable<String> getCompletionProposals(
				Context context, XsdTypeDefinition previousType, 
				final String prefix, int pos) {
			
			if (getTextRange(context).includes(pos) || getNextStep() == null) {
				Transformer<String, String> transformer = new TransformerAdapter<String, String>() {
					@Override
					public String transform(String s) {
						return StringTools.concatenate(prefix, s);
					}
				};
				return IterableTools.transform(
								IterableTools.transform(
										IterableTools.concatenate(
												getTextProposals(context, previousType),
												getAttributeProposals(context, previousType),
												getElementProposals(context, previousType)),
										transformer),
								StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
			}
			
			Step nextStep = getNextStep();
			XsdTypeDefinition nextType = resolveNextType(context, previousType);
			if (nextStep != null && nextType != null) {
				return nextStep.getCompletionProposals(context, nextType, prefix + getValue() + DELIM, pos);
			}
			
			return new SingleElementIterable(TEXT);
		}
		
		protected Iterable<String> getTextProposals(Context context, final XsdTypeDefinition xsdType) {
			return (xsdType.hasTextContent()) ? new SingleElementIterable(TEXT) : EmptyIterable.instance();						
		}
		
		protected Iterable<String> getAttributeProposals(Context context, final XsdTypeDefinition xsdType) {
			return IterableTools.concatenate(
					IterableTools.concatenate(
							IterableTools.transform(getXmlNsInfos(context), new XmlNsAttributeNamesTransformer(xsdType))),
					IterableTools.transform(xsdType.getAttributeNames(StringTools.EMPTY_STRING), new AttributeNameTransformer(null)));
		}
		
		protected Iterable<String> getElementProposals(Context context, final XsdTypeDefinition xsdType) {
			return IterableTools.concatenate(
					IterableTools.concatenate(
							IterableTools.transform(getXmlNsInfos(context), new XmlNsElementNamesTransformer(xsdType))),
					xsdType.getElementNames(StringTools.EMPTY_STRING, false));
		}
		
		protected Iterable<XmlNs> getXmlNsInfos(Context context) {
			JaxbPackageInfo pkgInfo = context.getJaxbPackage().getPackageInfo();
			XmlSchema xmlSchema = (pkgInfo == null) ? null : pkgInfo.getXmlSchema();
			return (xmlSchema == null) ? EmptyIterable.<XmlNs>instance() : xmlSchema.getXmlNsPrefixes();
		}
		
		protected void validate(Context context, XsdTypeDefinition previousType, List<IMessage> messages) {
			// no op
		}
		
		protected TextRange getTextRange(Context context) {
			return XPath.this.buildTextRange(context, this);
		}
	}

	protected class TextStep
			extends Step {
		
		protected TextStep() {
			super();
		}
		
		@Override
		protected String getValue() {
			return TEXT;
		}
		
		@Override
		protected XsdTypeDefinition
				resolveNextType(Context context, XsdTypeDefinition previousType) {
			
			return previousType;
		}
		
		@Override
		protected void validate(
				Context context, XsdTypeDefinition previousType, List<IMessage> messages) {
			
			if (getNextStep() != null) {
				messages.add(
						ELJaxbValidationMessageBuilder.buildMessage(
								IMessage.HIGH_SEVERITY,
								ELJaxbValidationMessages.XPATH__TEXT_SEGMENT_MUST_BE_LAST_SEGMENT,
								context.getContextObject(),
								getTextRange(context)));
			}
		}
	}
	
	
	public interface Context {
		
		JaxbNode getContextObject();
		
		JaxbPackage getJaxbPackage();
		
		TextRange getTextRange();
	}

	public static class XmlNsAttributeNamesTransformer
		extends TransformerAdapter<XmlNs, Iterable<String>>
	{
		protected final XsdTypeDefinition<?> xsdType;
		public XmlNsAttributeNamesTransformer(XsdTypeDefinition<?> xsdType) {
			super();
			this.xsdType = xsdType;
		}
		@Override
		public Iterable<String> transform(XmlNs xmlns) {
			return IterableTools.transform(this.xsdType.getAttributeNames(xmlns.getNamespaceURI()), new AttributeNameTransformer(xmlns.getPrefix()));
		}
	}

	public static class AttributeNameTransformer
		extends TransformerAdapter<String, String>
	{
		protected final String prefix;
		public AttributeNameTransformer(String prefix) {
			super();
			this.prefix = prefix;
		}
		@Override
		public String transform(String localName) {
			return XPath.attributeXPath(this.prefix, localName);
		}
	}

	public static class XmlNsElementNamesTransformer
		extends TransformerAdapter<XmlNs, Iterable<String>>
	{
		protected final XsdTypeDefinition<?> xsdType;
		public XmlNsElementNamesTransformer(XsdTypeDefinition<?> xsdType) {
			super();
			this.xsdType = xsdType;
		}
		@Override
		public Iterable<String> transform(XmlNs xmlns) {
			return IterableTools.transform(this.xsdType.getElementNames(xmlns.getNamespaceURI(), false), new ElementNameTransformer(xmlns.getPrefix()));
		}
	}

	public static class ElementNameTransformer
		extends TransformerAdapter<String, String>
	{
		protected final String prefix;
		public ElementNameTransformer(String prefix) {
			super();
			this.prefix = prefix;
		}
		@Override
		public String transform(String localName) {
			return XPath.elementXPath(this.prefix, localName);
		}
	}
}
