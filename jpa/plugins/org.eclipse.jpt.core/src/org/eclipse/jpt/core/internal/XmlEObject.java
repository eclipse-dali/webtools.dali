/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
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
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getXmlEObject()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlEObject extends JpaEObject implements IXmlEObject
{
	protected IDOMNode node;

	/**
	 * Sets of "insignificant" feature ids, keyed by class.
	 * This is built up lazily, as the objects are modified.
	 */
	private static final Map<Class, Set<Integer>> insignificantXmlFeatureIdSets = new Hashtable<Class, Set<Integer>>();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEObject() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaCorePackage.Literals.XML_EOBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated NOT
	 */
	public IJpaFile getJpaFile() {
		IJpaRootContentNode root = getRoot();
		return (root == null) ? null : root.getJpaFile();
	}

	@Override
	public EList<Adapter> eAdapters() {
		if (this.eAdapters == null) {
			this.eAdapters = new XmlEAdapterList(this);
		}
		return this.eAdapters;
	}

	public IDOMNode getNode() {
		return this.node;
	}
	protected class XmlEAdapterList extends EAdapterList
	{
		public XmlEAdapterList(Notifier notifier) {
			super(notifier);
		}

		@Override
		protected void didAdd(int index, Object newObject) {
			super.didAdd(index, newObject);
			try {
				node = (IDOMNode) ClassTools.executeMethod(newObject, "getNode");
			}
			catch (RuntimeException re) {
				// nothing to do
			}
		}

		@Override
		protected void didRemove(int index, Object oldObject) {
			super.didRemove(index, oldObject);
			if ((oldObject instanceof EMF2DOMAdapter) && (((EMF2DOMAdapter) oldObject).getNode() == XmlEObject.this.node)) {
				XmlEObject.this.node = null;
			}
		}
	}

	public boolean isAllFeaturesUnset() {
		for (EStructuralFeature feature : eClass().getEAllStructuralFeatures()) {
			if (xmlFeatureIsInsignificant(feature.getFeatureID())) {
				continue;
			}
			if (feature instanceof EReference) {
				Object object = eGet(feature);
				if (object instanceof Collection) {
					if (eIsSet(feature)) {
						return false;
					}
				}
				else {
					XmlEObject eObject = (XmlEObject) eGet(feature);
					if (eObject != null) {
						return eObject.isAllFeaturesUnset();
					}
				}
			}
			else if (eIsSet(feature)) {
				return false;
			}
		}
		return true;
	}

	public IJpaRootContentNode getRoot() {
		XmlEObject container = (XmlEObject) eContainer();
		return (container == null) ? null : container.getRoot();
	}

	public IResource getResource() {
		return getJpaFile().getResource();
	}

	public ITextRange getTextRange() {
		return buildTextRange(node);
	}

	protected ITextRange buildTextRange(IDOMNode domNode) {
		return new DOMNodeTextRange(domNode);
	}

	protected boolean xmlFeatureIsSignificant(int featureId) {
		return !this.xmlFeatureIsInsignificant(featureId);
	}

	protected boolean xmlFeatureIsInsignificant(int featureId) {
		return this.insignificantXmlFeatureIds().contains(featureId);
	}

	/**
	 * Return a set of the xml object's "insignificant" feature ids.
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
	protected Set<Integer> insignificantXmlFeatureIds() {
		synchronized (insignificantXmlFeatureIdSets) {
			Set<Integer> insignificantXmlFeatureIds = insignificantXmlFeatureIdSets.get(this.getClass());
			if (insignificantXmlFeatureIds == null) {
				insignificantXmlFeatureIds = new HashSet<Integer>();
				this.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
				insignificantXmlFeatureIdSets.put(this.getClass(), insignificantXmlFeatureIds);
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
	/**
	 * Implementation of ITextRange that adapts a IDOMNode.
	 */
	private static class DOMNodeTextRange implements ITextRange
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
			if ( ! (o instanceof ITextRange)) {
				return false;
			}
			ITextRange r = (ITextRange) o;
			return (r.getOffset() == this.getOffset())
					&& (r.getLength() == this.getLength());
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