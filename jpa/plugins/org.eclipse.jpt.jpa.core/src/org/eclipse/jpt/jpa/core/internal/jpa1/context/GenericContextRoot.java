/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.EmptyTextRange;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.JpaMetamodelSynchronizer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.JpaContextRoot2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * the context model root
 */
public class GenericContextRoot
	extends AbstractJpaContextModel<JpaContextModel>
	implements JpaContextRoot2_0
{
	/* This object has no parent, so it must point to the JPA project explicitly. */
	protected final JpaProject jpaProject;

	/* Main context object. */
	protected PersistenceXml persistenceXml;


	public GenericContextRoot(JpaProject jpaProject) {
		super(null);  // the JPA project is not really a "parent"...

		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		this.persistenceXml = this.buildPersistenceXml();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncPersistenceXml(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updatePersistenceXml(monitor);
	}


	// ********** persistence.xml **********

	public PersistenceXml getPersistenceXml() {
		return this.persistenceXml;
	}

	protected void setPersistenceXml(PersistenceXml persistenceXml) {
		PersistenceXml old = this.persistenceXml;
		this.persistenceXml = persistenceXml;
		this.firePropertyChanged(PERSISTENCE_XML_PROPERTY, old, persistenceXml);
	}

	protected PersistenceXml buildPersistenceXml() {
		JptXmlResource xmlResource = this.resolvePersistenceXmlResource();
		return (xmlResource == null) ? null : this.buildPersistenceXml(xmlResource);
	}

	protected void syncPersistenceXml(IProgressMonitor monitor) {
		this.syncPersistenceXml(true, monitor);
	}

	/**
	 * We call this method from both {@link #syncPersistenceXml(IProgressMonitor)} and
	 * {@link #updatePersistenceXml(IProgressMonitor)} because<ul>
	 * <li>a <em>sync</em> will occur when the file is edited directly;
	 *     and the user could modify something (e.g. the version number) that
	 *     triggers the file being "resolved" or not;
	 *     see {@link #resolvePersistenceXmlResource()}
	 * <li>an <em>update</em> will occur whenever the entire file is added or
	 *     removed
	 * </ul>
	 */
	protected void syncPersistenceXml(boolean sync, IProgressMonitor monitor) {
		JptXmlResource xmlResource = this.resolvePersistenceXmlResource();
		if (xmlResource == null) {
			this.setPersistenceXml(null);
		} else {
			if (this.persistenceXml == null) {
				this.setPersistenceXml(this.buildPersistenceXml(xmlResource));
			} else {
				if (sync) {
					this.persistenceXml.synchronizeWithResourceModel(monitor);
				} else {
					this.persistenceXml.update(monitor);
				}
			}
		}
	}

	protected JptXmlResource resolvePersistenceXmlResource() {
		JptXmlResource xmlResource = this.jpaProject.getPersistenceXmlResource();
		if (xmlResource == null) {
			return null;
		}
		if (xmlResource.isReverting()) {
			// 308254 - this can happen when persistence.xml is closed without saving;
			// the model is completely whacked in another thread - so wipe our model(?)
			return null;
		}
		JptResourceType resourceType = xmlResource.getResourceType();
		if (resourceType == null) {
			return null;
		}
		if ( ! this.getJpaPlatform().supportsResourceType(resourceType)) {
			return null;
		}
		return xmlResource;
	}

	/**
	 * pre-condition: 'xmlResource' is not <code>null</code>
	 */
	protected PersistenceXml buildPersistenceXml(JptXmlResource xmlResource) {
		return this.getJpaFactory().buildPersistenceXml(this, xmlResource);
	}

	protected void updatePersistenceXml(IProgressMonitor monitor) {
		this.syncPersistenceXml(false, monitor);
	}


	// ********** misc **********

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public void stateChanged() {
		super.stateChanged();
		// forward to JPA project
		this.jpaProject.stateChanged();
	}

	@Override
	public JpaProject getJpaProject() {
		return this.jpaProject;
	}

	@Override
	public IResource getResource() {
		return this.getProject();
	}

	protected IProject getProject() {
		return this.jpaProject.getProject();
	}

	@Override
	public PersistenceUnit getPersistenceUnit() {
		return null;
	}

	@Override
	public MappingFile.Root getMappingFileRoot() {
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.jpaProject.getName());
	}


	// ********** metamodel **********

	public void initializeMetamodel() {
		if (this.persistenceXml != null) {
			((PersistenceXml2_0) this.persistenceXml).initializeMetamodel();
		}
	}

	public IStatus synchronizeMetamodel(IProgressMonitor monitor) {
		return (this.persistenceXml != null) ?
				((PersistenceXml2_0) this.persistenceXml).synchronizeMetamodel(monitor) :
				Status.OK_STATUS;
	}

	public void disposeMetamodel() {
		if (this.persistenceXml != null) {
			((PersistenceXml2_0) this.persistenceXml).disposeMetamodel();
		}
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return EmptyTextRange.instance(); //?
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (this.persistenceXml == null) {
			messages.add(buildPersistenceXmlValidationMessage());
			return;
		}
		if ( ! this.jpaProject.discoversAnnotatedClasses()) {
			this.validateOrphanClasses(messages);
		}
		this.persistenceXml.validate(messages, reporter);
	}

	protected IMessage buildPersistenceXmlValidationMessage() {
		IFile file = getPlatformFile();
		if (file != null && file.exists()) {
			JptXmlResource xmlResource = this.jpaProject.getPersistenceXmlResource();
			if (xmlResource != null 
					&& ! getJpaPlatform().supportsResourceType(xmlResource.getResourceType())) {
				return ValidationMessageTools.buildValidationMessage(file, 
					JptJpaCoreValidationMessages.PERSISTENCE_XML_UNSUPPORTED_CONTENT);
			}
			return ValidationMessageTools.buildValidationMessage(file,
				JptJpaCoreValidationMessages.PERSISTENCE_XML_INVALID_CONTENT);
		}
		return this.buildValidationMessage(JptJpaCoreValidationMessages.PROJECT_NO_PERSISTENCE_XML);
	}

	protected IFile getPlatformFile() {
		return this.jpaProject.getPlatformFile(XmlPersistence.DEFAULT_RUNTIME_PATH);
	}

	protected void validateOrphanClasses(List<IMessage> messages) {
		Persistence persistence = this.persistenceXml.getRoot();
		if (persistence == null) {
			return;  // handled with other validation
		}
		if (persistence.getPersistenceUnitsSize() != 1) {
			return;  // the context model currently only supports 1 persistence unit
		}

		PersistenceUnit persistenceUnit = persistence.getPersistenceUnits().iterator().next();
		HashBag<JavaResourceAbstractType> annotatedTypes = CollectionTools.hashBag(this.jpaProject.getAnnotatedJavaSourceTypes());
		HashBag<JavaResourceAbstractType> orphans = annotatedTypes.clone();
		for (JavaResourceAbstractType jrat : annotatedTypes) {
			if (persistenceUnit.specifiesManagedType(jrat.getTypeBinding().getQualifiedName())) {
				orphans.remove(jrat);
			}
			else if (JpaMetamodelSynchronizer2_0.MetamodelTools.isMetamodel(jrat)) {
				orphans.remove(jrat);
			}
		}

		Iterable<String> managedTypeAnnotationNames = this.jpaProject.getManagedTypeAnnotationNames();
		for (JavaResourceAbstractType jrat : orphans) {
			if (jrat.isAnnotatedWithAnyOf(managedTypeAnnotationNames)) {
				messages.add(
					ValidationMessageTools.buildValidationMessage(
						jrat.getFile(),
						jrat.getNameTextRange(),
						JptJpaCoreValidationMessages.TYPE_MANAGED_BUT_NOT_LISTED_IN_PERSISTENCE_XML,
						jrat.getTypeBinding().getQualifiedName()
					)
				);
			}
			else {
				messages.add(
					ValidationMessageTools.buildValidationMessage(
						jrat.getFile(),
						jrat.getNameTextRange(),
						JptJpaCoreValidationMessages.TYPE_ANNOTATED_BUT_NOT_LISTED_IN_PERSISTENCE_XML,
						jrat.getTypeBinding().getQualifiedName()
					)
				);
			}
		}
	}
}
