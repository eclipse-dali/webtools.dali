/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.common;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.utility.AbstractTextRange;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml EObject</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.common.CommonPackage#getXmlEObject()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractJpaEObject
	extends EObjectImpl
	implements JpaEObject
{
	protected IDOMNode node;
	
	/**
	 * Sets of "insignificant" feature ids, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final Hashtable<Class<? extends AbstractJpaEObject>, HashSet<Integer>> insignificantFeatureIdSets = new Hashtable<Class<? extends AbstractJpaEObject>, HashSet<Integer>>();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractJpaEObject() {
		super();
	}


	// ********** JpaEObject implementation **********

	public boolean isAllFeaturesUnset() {
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

	protected JpaXmlResource getXmlResource() {
		return (JpaXmlResource) this.eResource();
	}

	protected boolean featureIsSignificant(int featureId) {
		return ! this.featureIsInsignificant(featureId);
	}

	protected boolean featureIsInsignificant(int featureId) {
		return this.insignificantFeatureIds().contains(new Integer(featureId));
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
	 * @see isAllFeaturesUnset()
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
	 * Return a text range for the specified attribute node.
	 * If the attribute node does not exist, return a text range for this object's
	 * node
	 */
	protected TextRange getAttributeTextRange(String attributeName) {
		IDOMNode attributeNode = this.getAttributeNode(attributeName);
		return (attributeNode != null) ? buildTextRange(attributeNode) : this.getValidationTextRange();
	}
	
	protected IDOMNode getAttributeNode(String attributeName) {
		return (IDOMNode) this.node.getAttributes().getNamedItem(attributeName);
	}
	
	/**
	 * Return a text range for the specified element node.
	 * If the element node does not exist, return a text range for this object's
	 * node
	 */
	protected TextRange getElementTextRange(String elementName) {
		IDOMNode elementNode = this.getElementNode(elementName);
		return (elementNode != null) ? buildTextRange(elementNode) : this.getValidationTextRange();
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
	
	protected TextRange getFullTextRange() {
		return buildTextRange(this.node);
	}
	
	protected static TextRange buildTextRange(IDOMNode domNode) {
		return (domNode == null) ? null : new DOMNodeTextRange(domNode);
	}
	
	public boolean containsOffset(int textOffset) {
		return (this.node == null) ? false : this.node.contains(textOffset);
	}


	// ********** custom adapter list **********

	protected class XmlEAdapterList<E extends Object & Adapter>
		extends EAdapterList<E>
	{
		public XmlEAdapterList(Notifier notifier) {
			super(notifier);
		}

		@Override
		protected void didAdd(int index, E newObject) {
			super.didAdd(index, newObject);
			if (newObject instanceof EMF2DOMAdapter) {
				Object n = ((EMF2DOMAdapter) newObject).getNode();
				if (n instanceof IDOMNode) {
					AbstractJpaEObject.this.node = (IDOMNode) n;
				}
			}
		}

		@Override
		protected void didRemove(int index, E oldObject) {
			if ((oldObject instanceof EMF2DOMAdapter) &&
					(((EMF2DOMAdapter) oldObject).getNode() == AbstractJpaEObject.this.node)) {
				AbstractJpaEObject.this.node = null;
			}
			super.didRemove(index, oldObject);
		}
	}


	// ********** DOM node text range **********

	/**
	 * Adapt an IDOMNode to the TextRange interface.
	 */
	protected static class DOMNodeTextRange
		extends AbstractTextRange
	{
		private final IDOMNode node;

		DOMNodeTextRange(IDOMNode node) {
			super();
			this.node = node;
		}

		public int getOffset() {
			return this.node.getStartOffset();
		}

		public int getLength() {
			if (this.node.getNodeType() == Node.ELEMENT_NODE) {
				return ((IDOMElement) this.node).getStartEndOffset() - this.node.getStartOffset();
			}
			return this.node.getLength();
		}

		public int getLineNumber() {
			return this.node.getStructuredDocument().getLineOfOffset(this.getOffset()) + 1;
		}

	}

}
