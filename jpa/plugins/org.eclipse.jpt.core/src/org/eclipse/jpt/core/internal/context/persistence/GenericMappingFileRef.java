/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.List;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericMappingFileRef extends AbstractPersistenceJpaContextNode 
	implements MappingFileRef
{
	//this is null for the implied mappingFileRef case
	protected XmlMappingFileRef xmlMappingFileRef;
	
	protected String fileName;
	
	protected OrmXml ormXml;
	
	public GenericMappingFileRef(PersistenceUnit parent, XmlMappingFileRef mappingFileRef) {
		super(parent);
		this.initialize(mappingFileRef);
	}
	
	public String getId() {
		return PersistenceStructureNodes.MAPPING_FILE_REF_ID;
	}

	
	public boolean isVirtual() {
		return xmlMappingFileRef == null;
	}
	
	
	// **************** file name **********************************************
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String newFileName) {
		this.xmlMappingFileRef.setFileName(newFileName);
		setFileName_(newFileName);
	}
	
	protected void setFileName_(String newFileName) {
		String oldFileName = this.fileName;
		this.fileName = newFileName;
		firePropertyChanged(FILE_NAME_PROPERTY, oldFileName, newFileName);
	}
	
	public OrmXml getOrmXml() {
		return this.ormXml;
	}
	
	protected void setOrmXml(OrmXml newOrmXml) {
		OrmXml oldOrmXml = this.ormXml;
		this.ormXml = newOrmXml;
		firePropertyChanged(ORM_XML_PROPERTY, oldOrmXml, newOrmXml);
	}
	
	
	// **************** updating ***********************************************
	
	protected void initialize(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		initializeFileName();
		initializeOrmXml();
	}
	
	protected void initializeFileName() {
		if (isVirtual()) {
			fileName = JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH;
		}
		else {
			fileName = xmlMappingFileRef.getFileName();
		}
	}
	
	protected void initializeOrmXml() {
		if (fileName != null) {
			OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(getJpaProject().getProject());
			OrmResource ormResource = oae.getResource(fileName);
			
			if (ormResource != null && ormResource.exists()) {
				ormXml = buildOrmXml(ormResource);
			}
			oae.dispose();
		}
	}

	public void update(XmlMappingFileRef mappingFileRef) {
		xmlMappingFileRef = mappingFileRef;
		updateFileName();
		updateOrmXml();
	}
	
	protected void updateFileName() {
		if (isVirtual()) {
			setFileName_(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		}
		else {
			setFileName_(xmlMappingFileRef.getFileName());
		}
	}
	
	protected void updateOrmXml() {
		if (fileName != null) {
			OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(getJpaProject().getProject());
			OrmResource ormResource = oae.getResource(fileName);
			if (ormResource != null && ormResource.exists()) {
				if (ormXml != null) {
					ormXml.update(ormResource);
				}
				else {
					setOrmXml(buildOrmXml(ormResource));
				}
			}
			else {
				setOrmXml(null);
			}
			oae.dispose();
		}
		else {
			setOrmXml(null);
		}
	}
	
	protected OrmXml buildOrmXml(OrmResource ormResource) {
		return getJpaFactory().buildOrmXml(this, ormResource);
	}
	
	
	// *************************************************************************
	
	public PersistenceUnitDefaults getPersistenceUnitDefaults() {
		if (getOrmXml() != null) {
			return getOrmXml().getPersistenceUnitDefaults();
		}
		return null;
	}
	
	public OrmPersistentType persistentTypeFor(String fullyQualifiedTypeName) {
		if (getOrmXml() != null) {
			return getOrmXml().persistentTypeFor(fullyQualifiedTypeName);
		}
		return null;
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (this.xmlMappingFileRef == null) {
			return false;
		}
		return this.xmlMappingFileRef.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return this.xmlMappingFileRef.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		if (isVirtual()) {
			return getPersistenceUnit().getValidationTextRange();
		}
		return this.xmlMappingFileRef.getValidationTextRange();
	}
	
	//**************** Validation *************************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		this.addUnspecifiedMappingFileMessage(messages);
		this.addUnresolvedMappingFileMessage(messages);
		this.addInvalidMappingFileContentMessage(messages);
		if (getOrmXml() != null) {
			getOrmXml().addToMessages(messages);
		}
	}
	
	
	protected void addUnspecifiedMappingFileMessage(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(getFileName())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE,
					this, 
					getValidationTextRange())
			);
		}
	}
	
	protected void addUnresolvedMappingFileMessage(List<IMessage> messages) {
		if (!StringTools.stringIsEmpty(getFileName()) && getOrmXml() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
					new String[] {getFileName()}, 
					this, 
					getValidationTextRange()) 
			);
		}
	}
	
	protected void addInvalidMappingFileContentMessage(List<IMessage> messages) {
		if (getOrmXml() != null 
				&& getOrmXml().getEntityMappings() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,
					new String[] {getFileName()}, 
					this,
					getValidationTextRange())
			);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getFileName());
	}
}
