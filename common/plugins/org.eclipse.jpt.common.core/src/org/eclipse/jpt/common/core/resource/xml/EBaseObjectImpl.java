/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.xml;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMAttr;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.2
 */
public abstract class EBaseObjectImpl
	extends EObjectImpl
	implements EBaseObject
{
	protected IDOMNode node;
	
	/**
	 * Sets of "insignificant" feature ids, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final Hashtable<Class<? extends EBaseObjectImpl>, HashSet<Integer>> insignificantFeatureIdSets = new Hashtable<Class<? extends EBaseObjectImpl>, HashSet<Integer>>();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EBaseObjectImpl() {
		super();
	}


	// ********** EBaseObject implementation **********

	public boolean isUnset() {
		for (EStructuralFeature feature : this.eClass().getEAllStructuralFeatures()) {
			if (this.eIsSet(feature)) {
				return false;
			}
		}
		return true;
	}


	// ********** change notification **********

	/**
	 * override to build a custom list for the adapters
	 */
	@Override
	public EList<Adapter> eAdapters() {
		if (this.eAdapters == null) {
			this.eAdapters = new XmlEAdapterList<Adapter>(this);
		}
		return this.eAdapters;
	}

	/**
	 * override to prevent notification when the object's state is unchanged
	 */
	@Override
	public void eNotify(Notification notification) {
		if ( ! notification.isTouch()) {
			super.eNotify(notification);
			this.featureChanged(notification.getFeatureID(this.getClass()));
		}
	}

	protected void featureChanged(int featureId) {
		if (this.featureIsSignificant(featureId)) { 
			this.getXmlResource().resourceModelChanged();
		}
	}

	protected JptXmlResource getXmlResource() {
		return (JptXmlResource) this.eResource();
	}

	protected boolean featureIsSignificant(int featureId) {
		return ! this.featureIsInsignificant(featureId);
	}

	protected boolean featureIsInsignificant(int featureId) {
		return this.insignificantFeatureIds().contains(Integer.valueOf(featureId));
	}

	/**
	 * Return a set of the object's "insignificant" feature ids.
	 * These are the EMF features that will not be used to determine if all
	 * the features are unset.  We use this to determine when to remove 
	 * an element from the xml.
	 * 
	 * If you need instance-based calculation of your xml "insignificant" aspects,
	 * override this method. If class-based calculation is sufficient,
	 * override #addInsignificantXmlFeatureIdsTo(Set).
	 * 
	 * @see #isUnset()
	 */
	protected Set<Integer> insignificantFeatureIds() {
		synchronized (insignificantFeatureIdSets) {
			HashSet<Integer> insignificantXmlFeatureIds = insignificantFeatureIdSets.get(this.getClass());
			if (insignificantXmlFeatureIds == null) {
				insignificantXmlFeatureIds = new HashSet<Integer>();
				this.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
				insignificantFeatureIdSets.put(this.getClass(), insignificantXmlFeatureIds);
			}
			return insignificantXmlFeatureIds;
		}
	}

	/**
	 * Add the object's "insignificant" feature ids to the specified set.
	 * These are the EMF features that, when they change, will NOT cause the
	 * object (or its containing tree) to be updated, i.e. defaults calculated.
	 * If class-based calculation of your "insignificant" features is sufficient,
	 * override this method. If you need instance-based calculation,
	 * override #insignificantXmlFeatureIds().
	 */
	protected void addInsignificantXmlFeatureIdsTo(@SuppressWarnings("unused") Set<Integer> insignificantXmlFeatureIds) {
	// when you override this method, don't forget to include:
	//	super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
	}


	// ********** text ranges **********
	
	/**
	 * Return a text range for the "text" node.
	 * If the text node does not exist, return a text range for this object's node
	 */
	protected TextRange getTextTextRange() {
		IDOMNode textNode = this.getTextNode();
		return (textNode != null) ? this.buildTextRange(textNode) : this.getValidationTextRange();
	}
	
	protected IDOMNode getTextNode() {
		return this.selectTextNode(this.node.getChildNodes());
	}

	
	protected IDOMNode selectTextNode(NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			IDOMNode n = (IDOMNode) nodes.item(i);
			if (n.getNodeType() == Node.TEXT_NODE) {
				return n;
			}
		}
		return null;
	}

	/**
	 * Return a text range for the specified attribute node.
	 * If the attribute node does not exist, return a text range for this object's
	 * node
	 */
	protected TextRange getAttributeTextRange(String attributeName) {
		IDOMNode attributeNode = this.getAttributeNode(attributeName);
		return (attributeNode != null) ? this.buildTextRange(attributeNode) : this.getValidationTextRange();
	}
	
	protected IDOMAttr getAttributeNode(String attributeName) {
		return (IDOMAttr) this.node.getAttributes().getNamedItem(attributeName);
	}
	
	/**
	 * Return a text range for the specified element node.
	 * If the element node does not exist, return a text range for this object's
	 * node
	 */
	protected TextRange getElementTextRange(String elementName) {
		IDOMNode elementNode = this.getElementNode(elementName);
		return (elementNode != null) ? this.buildTextRange(elementNode) : this.getValidationTextRange();
	}
	
	/**
	 * Returns the first element node with the given name, if one exists
	 */
	protected IDOMNode getElementNode(String elementName) {
		NodeList children = this.node.getChildNodes();
		for (int i = 0; i < children.getLength(); i ++) {
			IDOMNode child = (IDOMNode) children.item(i);
			if ((child.getNodeType() == Node.ELEMENT_NODE)
					&& elementName.equals(child.getNodeName())) {
				return child;
			}
		}
		return null;
	}
	
	public TextRange getValidationTextRange() {
		return this.getFullTextRange();
	}
	
	public TextRange getSelectionTextRange() {
		return this.getFullTextRange();
	}
	
	public TextRange getFullTextRange() {
		return this.buildTextRange(this.node);
	}

	protected TextRange buildTextRange(IDOMNode domNode) {
		return (domNode == null) ? null : this.buildTextRange_(domNode, null);
	}

	protected TextRange buildTextRange(IDOMNode domNode, TextRange textRange) {
		return (domNode == null) ? null : this.buildTextRange_(domNode, textRange);
	}

	/**
	 * pre-condition: the specified DOM node is not <code>null</code>
	 */
	protected TextRange buildTextRange_(IDOMNode domNode, TextRange textRange) {
		int offset = domNode.getStartOffset();
		int length = (domNode.getNodeType() == Node.ELEMENT_NODE) ?
						(((IDOMElement) domNode).getStartEndOffset() - offset) :
						domNode.getLength();
		int lineNumber = domNode.getStructuredDocument().getLineOfOffset(offset) + 1;
		return (textRange == null) ?
				new SimpleTextRange(offset, length, lineNumber) :
				textRange.buildTextRange(offset, length, lineNumber);
	}

	public boolean containsOffset(int textOffset) {
		return (this.node != null) && this.node.contains(textOffset);
	}


	// ********** content assist **********
	
	/**
	 * Return a text range for the specified attribute node.
	 * If the attribute node does not exist, return null
	 * 
	 * This is used for content assist to return the exact text range of an attribute.
	 * It is different from the text range used by validation which returns
	 * the parent's text range if the specified attribute node does not exist
	 */
	protected TextRange getAttributeCodeAssistTextRange(String attributeName) {
		IDOMNode attributeNode = this.getAttributeNode(attributeName);
		return (attributeNode != null) ? this.buildTextRange(attributeNode) : null;
	}
	
	/**
	 * Return a text range for the specified element node.
	 * If the element node does not exist, return null
	 * 
	 * This is used for content assist to return the exact text range of an element.
	 * It is different from the text range used by validation which returns
	 * the parent's text range if the specified element node does not exist
	 */
	protected TextRange getElementCodeAssistTextRange(String elementName) {
		IDOMNode elementNode = this.getElementNode(elementName);
		return (elementNode != null) ? this.buildElementCodeAssistTextRange(elementNode) : null;
	}
	
	protected TextRange buildElementCodeAssistTextRange(IDOMNode domNode) {
		return (domNode == null) ? null : this.buildElementCodeAssistTextRange(domNode, null);
	}
	
	protected TextRange buildElementCodeAssistTextRange(IDOMNode domNode, TextRange textRange) {
		return (domNode == null) ? null : this.buildElementCodeAssistTextRange_(domNode, textRange);
	}
	/**
	 * pre-condition: the specified DOM node is not <code>null</code>
	 */
	protected TextRange buildElementCodeAssistTextRange_(IDOMNode domNode, TextRange textRange) {
		int offset = domNode.getStartOffset();
		int length = (domNode.getNodeType() == Node.ELEMENT_NODE) ?
						(((IDOMElement) domNode).getEndStartOffset() - offset) :
						domNode.getLength();
		int lineNumber = domNode.getStructuredDocument().getLineOfOffset(offset) + 1;
		return (textRange == null) ?
				new SimpleTextRange(offset, length, lineNumber) :
				textRange.buildTextRange(offset, length, lineNumber);
	}

	// ********** Refactoring TextEdits **********
	
	public DeleteEdit createDeleteEdit() {
		int deletionOffset = getDeletionOffset();
		int deletionLength = this.node.getEndOffset() - deletionOffset;
		return new DeleteEdit(deletionOffset, deletionLength);
	}

	public int getNodeEndOffset() {
		return this.node.getEndOffset();
	}

	/**
	 * deletion offset needs to include any text that is before the node
	 */
	protected int getDeletionOffset() {
		int emptyTextLength = 0;
		Node previousSibling = this.node.getPreviousSibling();
		if (previousSibling != null && previousSibling.getNodeType() == Node.TEXT_NODE) {
			emptyTextLength = ((Text) previousSibling).getLength();
		}
		return this.node.getStartOffset() - emptyTextLength;
	}


	// ********** custom adapter list **********

	protected static class XmlEAdapterList<E extends Object & Adapter>
		extends EAdapterList<E>
	{
		private static final long serialVersionUID = 1L;

		public XmlEAdapterList(EBaseObjectImpl jpaEObject) {
			super(jpaEObject);
		}

		@Override
		protected void didAdd(int index, E newObject) {
			super.didAdd(index, newObject);
			if (newObject instanceof EMF2DOMAdapter) {
				Object n = ((EMF2DOMAdapter) newObject).getNode();
				if (n instanceof IDOMNode) {
					((EBaseObjectImpl) this.notifier).node = (IDOMNode) n;
				}
			}
		}

		@Override
		protected void didRemove(int index, E oldObject) {
			if ((oldObject instanceof EMF2DOMAdapter) &&
					(((EMF2DOMAdapter) oldObject).getNode() == ((EBaseObjectImpl) this.notifier).node)) {
				((EBaseObjectImpl) this.notifier).node = null;
			}
			super.didRemove(index, oldObject);
		}
	}
}
