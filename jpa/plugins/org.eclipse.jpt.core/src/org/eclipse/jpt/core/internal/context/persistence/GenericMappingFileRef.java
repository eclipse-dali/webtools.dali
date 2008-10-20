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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.resource.JpaResourceModelProviderManager;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.JpaResourceModelProvider;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericMappingFileRef extends AbstractXmlContextNode 
	implements MappingFileRef
{
	//this is null for the implied mappingFileRef case
	protected XmlMappingFileRef xmlMappingFileRef;
	
	protected String fileName;
	
	protected MappingFile mappingFile;
	
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
	
	public MappingFile getMappingFile() {
		return this.mappingFile;
	}
	
	protected void setMappingFile(MappingFile newMappingFile) {
		MappingFile oldMappingFile = this.mappingFile;
		this.mappingFile = newMappingFile;
		firePropertyChanged(MAPPING_FILE_PROPERTY, oldMappingFile, newMappingFile);
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
			OrmResourceModelProvider modelProvider =
				OrmResourceModelProvider.getModelProvider(getJpaProject().getProject(), fileName);
			OrmResource ormResource = modelProvider.getResource();
			
			if (ormResource != null && ormResource.exists()) {
				mappingFile = buildMappingFile(ormResource);
			}
		}
	}

	public void update(XmlMappingFileRef mappingFileRef) {
		xmlMappingFileRef = mappingFileRef;
		updateFileName();
		updateMappingFile();
	}
	
	protected void updateFileName() {
		if (isVirtual()) {
			setFileName_(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		}
		else {
			setFileName_(xmlMappingFileRef.getFileName());
		}
	}
	
	protected void updateMappingFile() {
		if (fileName != null) {
			IProject project = getJpaProject().getProject();
			IVirtualFile vFile = ComponentCore.createFile(project, new Path(fileName));
			IFile realFile = vFile.getUnderlyingFile();
			
			if ((realFile != null) && realFile.exists()) {
				JpaResourceModelProvider modelProvider = JpaResourceModelProviderManager.instance().getModelProvider(realFile);
				JpaXmlResource resource = (modelProvider == null) ? null : modelProvider.getResource();
				if (resource != null) {
					if (this.mappingFile != null && ! resource.equals(this.mappingFile.getXmlResource())) {
						this.mappingFile.dispose();
					}
					if (this.mappingFile == null) {
						setMappingFile(buildMappingFile(resource));
					}
					else {
						this.mappingFile.update(resource);
					}
					return;
				}
			}
		}
		
		if (this.mappingFile != null) {
			this.mappingFile.dispose();
			setMappingFile(null);
		}
	}
	
	protected MappingFile buildMappingFile(JpaXmlResource resource) {
		XmlContextNode context = this.getJpaFactory().buildContextNode(this, resource);
		try {
			return (MappingFile) context;
		} catch (ClassCastException ex) {
			// resource does not correspond to a mapping file
			return null;
		}
	}
	
	
	// *************************************************************************
	
	public MappingFilePersistenceUnitDefaults getPersistenceUnitDefaults() {
		if ((this.mappingFile != null) && (this.mappingFile.getRoot() != null)) {
			return this.mappingFile.getRoot().getPersistenceUnitDefaults();
		}
		return null;
	}
	
	public PersistentType getPersistentType(String fullyQualifiedTypeName) {
		if (this.mappingFile != null) {
			return this.mappingFile.getPersistentType(fullyQualifiedTypeName);
		}
		return null;
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
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

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.fileName);
	}

	public void dispose() {
		if (this.mappingFile != null) {
			this.mappingFile.dispose();
		}
	}


	//**************** Validation *************************

	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);

		if (StringTools.stringIsEmpty(this.fileName)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE,
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.mappingFile == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,
					new String[] {this.fileName},
					this,
					this.getValidationTextRange()
				)
			);
			return;
		}

		if (this.mappingFile.getRoot() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,
					new String[] {this.fileName},
					this,
					this.getValidationTextRange()
				)
			);
		}

		this.mappingFile.validate(messages);
	}

}
