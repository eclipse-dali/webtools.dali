/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import java.util.Collection;
import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * context model <code>persistence.xml</code> file
 */
public class GenericPersistenceXml
	extends AbstractPersistenceXmlContextNode
	implements PersistenceXml2_0
{
	/**
	 * If the XML resource's content type changes, the root context
	 * node will throw out its current persistence XML.
	 */
	protected final JptXmlResource xmlResource;  // never null

	/**
	 * The resource type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JptResourceType resourceType;

	/**
	 * The root element of the <code>persistence.xml</code> file.
	 */
	protected Persistence root;


	public GenericPersistenceXml(JpaRootContextNode parent, JptXmlResource xmlResource) {
		super(parent);
		this.checkXmlResource(xmlResource);
		this.xmlResource = xmlResource;
		this.resourceType = xmlResource.getResourceType();

		XmlPersistence xmlPersistence = (XmlPersistence) xmlResource.getRootObject();
		if (xmlPersistence != null) {
			this.root = this.buildRoot(xmlPersistence);
		}
	}


	// ********** synchronize/update **********

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml#synchronizeWithResourceModel()
	 */
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncPersistence();
	}

	protected void syncPersistence() {
		this.syncPersistence(true);
	}

	/**
	 * @see org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml#update()
	 */
	@Override
	public void update() {
		this.updatePersistence();	
	}

	protected void updatePersistence() {
		this.syncPersistence(false);
	}
	
	/**
	 * We call this method from both {@link #syncPersistence()} and
	 * {@link #updatePersistence()} because<ul>
	 * <li>a <em>sync</em> will occur when the file is edited directly;
	 *     the user could modify something (e.g. the version number) that 
	 *     causes the XML entity mappings to be rebuilt. 
	 * 
	 * <li>an <em>update</em> will occur whenever the JPA file is added or removed: 
	 *     when resource contents replaced from history EMF unloads the resource.
	 */
	protected void syncPersistence(boolean sync) {
		XmlPersistence oldXmlPersistence = (this.root == null) ? null : this.root.getXmlPersistence();
		XmlPersistence newXmlPersistence = (XmlPersistence) this.xmlResource.getRootObject();
		JptResourceType newResourceType = this.xmlResource.getResourceType();

		// If the old and new XML persistences are different instances,
		// we scrap the old context persistence and rebuild.
		// (This can happen when the resource model changes drastically,
		// such as a CVS checkout or an edit reversion.)
		if ((oldXmlPersistence != newXmlPersistence) ||
				(newXmlPersistence == null) || 
				this.valuesAreDifferent(this.resourceType, newResourceType)
		) {
			if (this.root != null) {
				this.root.dispose();
				this.setRoot(null);
			}
		}

		this.resourceType = newResourceType;

		if (newXmlPersistence != null) {
			if (this.root == null) {
				this.setRoot(this.buildRoot(newXmlPersistence));
			} else {
				// the context persistence already holds the XML persistence
				if (sync) {
					this.root.synchronizeWithResourceModel();
				}
				else {
					this.root.update();
				}
			}
		}
	}


	// ********** persistence **********

	public Persistence getRoot() {
		return this.root;
	}

	protected void setRoot(Persistence persistence) {
		Persistence old = this.root;
		this.root = persistence;
		this.firePropertyChanged(ROOT_PROPERTY, old, persistence);
	}

	protected Persistence buildRoot(XmlPersistence xmlPersistence) {
		return this.getContextNodeFactory().buildPersistence(this, xmlPersistence);
	}


	// ********** misc **********

	protected void checkXmlResource(JptXmlResource resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		if ( ! resource.getContentType().isKindOf(XmlPersistence.CONTENT_TYPE)) {
			throw new IllegalArgumentException("Content type is not 'persistence': " + resource); //$NON-NLS-1$
		}
	}

	public JptXmlResource getXmlResource() {
		return this.xmlResource;
	}

	@Override
	public IResource getResource() {
		return this.xmlResource.getFile();
	}

	@Override
	public JptResourceType getResourceType() {
		return this.xmlResource.getResourceType();
	}

	protected JpaFile getJpaFile() {
		return this.getJpaFile(this.xmlResource.getFile());
	}

	public boolean isLatestSupportedVersion() {
		return XmlFile_.isLatestSupportedVersion(this);
	}

	public boolean isGenericMappingFile() {
		return false;
	}

	// ********** metamodel **********

	public void initializeMetamodel() {
		if (this.root != null) {
			((Persistence2_0) this.root).initializeMetamodel();
		}
	}

	public IStatus synchronizeMetamodel(IProgressMonitor monitor) {
		return (this.root != null) ?
				((Persistence2_0) this.root).synchronizeMetamodel(monitor) :
				Status.OK_STATUS;
	}

	public void disposeMetamodel() {
		if (this.root != null) {
			((Persistence2_0) this.root).disposeMetamodel();
		}
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<PersistenceXml> getType() {
		return PersistenceXml.class;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		if (this.root.containsOffset(textOffset)) {
			return this.root.getStructureNode(textOffset);
		}
		return this;
	}

	// never actually selected
	public TextRange getSelectionTextRange() {
		return TextRange.Empty.instance();
	}

	public void gatherRootStructureNodes(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.root == null) {
			return;
		}
		if (this.getResource().equals(jpaFile.getFile())) {
			rootStructureNodes.add(this.root);
			return;
		}
		this.root.gatherRootStructureNodes(jpaFile, rootStructureNodes);
	}

	public void dispose() {
		if (this.root != null) {
			this.root.dispose();
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (this.root == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_XML_INVALID_CONTENT,
					this
				)
			);
			return;
		}

		this.root.validate(messages, reporter);
	}

	public TextRange getValidationTextRange() {
		// since this is the entire file, point to the top of the file
		return TextRange.Empty.instance();
	}
}
