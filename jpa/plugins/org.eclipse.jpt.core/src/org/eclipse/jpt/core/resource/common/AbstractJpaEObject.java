/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.common.CommonPackage#getXmlEObject()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractJpaEObject extends EObjectImpl implements JpaEObject
{
	protected IDOMNode node;
	
	/**
	 * Sets of "insignificant" feature ids, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final Map<Class, Set<Integer>> insignificantFeatureIdSets = new Hashtable<Class, Set<Integer>>();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractJpaEObject() {
		super();
	}
	
	
	// **************** IJpaEObject implementation *****************************
		
	public IResource platformResource() {
		return resource().getFile();
	}
	
	public JpaXmlResource resource() {
		return (JpaXmlResource) eResource();
	}
	
	/*
	 * Must be overridden by actual root object to return itself
	 */
	public JpaEObject root() {
		return ((JpaEObject) eContainer()).root();
		
	}
	
	
	// **************** change notification ************************************
	
	/**
	 * override to prevent notification when the object's state is unchanged
	 */
	@Override
	public void eNotify(Notification notification) {
		if (!notification.isTouch()) {
			super.eNotify(notification);
			this.featureChanged(notification.getFeatureID(this.getClass()));
		}
	}
	
	protected void featureChanged(int featureId) {
		if (this.featureIsSignificant(featureId)) { 
			resource().resourceChanged();
		}
	}
	
	protected boolean featureIsSignificant(int featureId) {
		return ! this.featureIsInsignificant(featureId);
	}
	
	protected boolean featureIsInsignificant(int featureId) {
		return this.insignificantFeatureIds().contains(featureId);
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
			Set<Integer> insignificantXmlFeatureIds = insignificantFeatureIdSets.get(this.getClass());
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
	 * object (or its containing tree) to be resynched, i.e. defaults calculated.
	 * If class-based calculation of your "insignificant" features is sufficient,
	 * override this method. If you need instance-based calculation,
	 * override #insignificantXmlFeatureIds().
	 */
	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
	// when you override this method, don't forget to include:
	//	super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
	}
	
	
	// *************************************************************************
	
	public boolean isAllFeaturesUnset() {
		for (EStructuralFeature feature : eClass().getEAllStructuralFeatures()) {
			if (eIsSet(feature)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public EList<Adapter> eAdapters() {
		if (this.eAdapters == null) {
			this.eAdapters = new XmlEAdapterList<Adapter>(this);
		}
		return this.eAdapters;
	}

	public IDOMNode getNode() {
		return this.node;
	}


	protected class XmlEAdapterList<E extends Object & Adapter> extends EAdapterList<E>
	{
		public XmlEAdapterList(Notifier notifier) {
			super(notifier);
		}

		@Override
		protected void didAdd(int index, E newObject) {
			super.didAdd(index, newObject);
			try {
				node = (IDOMNode) ClassTools.executeMethod(newObject, "getNode");
			}
			catch (RuntimeException re) {
				// nothing to do
			}
		}

		@Override
		protected void didRemove(int index, E oldObject) {
			super.didRemove(index, oldObject);
			if ((oldObject instanceof EMF2DOMAdapter) && (((EMF2DOMAdapter) oldObject).getNode() == AbstractJpaEObject.this.node)) {
				AbstractJpaEObject.this.node = null;
			}
		}
	}
	
	public TextRange validationTextRange() {
		return fullTextRange();
	}
	
	public TextRange selectionTextRange() {
		return fullTextRange();
	}
	
	public TextRange fullTextRange() {
		return buildTextRange(this.node);
	}
	
	protected TextRange buildTextRange(IDOMNode domNode) {
		if (domNode == null) {
			return null;
		}
		return new DOMNodeTextRange(domNode);
	}
	
	public boolean containsOffset(int textOffset) {
		if (node == null) {
			return false;
		}
		return node.contains(textOffset);
	}
	
	/**
	 * Implementation of ITextRange that adapts a IDOMNode.
	 */
	private static class DOMNodeTextRange implements TextRange
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
			return this.node.getStructuredDocument().getLineOfOffset(getOffset()) + 1;
		}

		public boolean includes(int index) {
			return (this.getOffset() <= index) && (index < this.end());
		}

		public boolean touches(int index) {
			return this.includes(index) || (index == this.end());
		}

		/**
		 * The end offset is "exclusive", i.e. the element at the end offset
		 * is not included in the range.
		 */
		private int end() {
			return this.getOffset() + this.getLength();
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof TextRange)) {
				return false;
			}
			TextRange r = (TextRange) o;
			return (r.getOffset() == this.getOffset()) && (r.getLength() == this.getLength());
		}

		@Override
		public int hashCode() {
			return this.getOffset() ^ this.getLength();
		}

		@Override
		public String toString() {
			String start = String.valueOf(this.getOffset());
			String end = String.valueOf(this.getOffset() + this.getLength() - 1);
			return StringTools.buildToStringFor(this, start + ", " + end);
		}
	}
}
