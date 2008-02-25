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

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;

public class GenericMappingFileRef extends AbstractJpaContextNode 
	implements MappingFileRef
{
	protected XmlMappingFileRef xmlMappingFileRef;
	
	protected String fileName;
	
	protected OrmXml ormXml;
	
	public GenericMappingFileRef(PersistenceUnit parent) {
		super(parent);
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
	
	public void initialize(XmlMappingFileRef mappingFileRef) {
		xmlMappingFileRef = mappingFileRef;
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
			OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(jpaProject().project());
			OrmResource ormResource = oae.getResource(fileName);
			
			if (ormResource != null && ormResource.exists()) {
				ormXml = createOrmXml(ormResource);
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
			OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(jpaProject().project());
			OrmResource ormResource = oae.getResource(fileName);
			if (ormResource != null && ormResource.exists()) {
				if (ormXml != null) {
					ormXml.update(ormResource);
				}
				else {
					setOrmXml(createOrmXml(ormResource));
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
	
	protected OrmXml createOrmXml(OrmResource ormResource) {
		OrmXml ormXml = jpaFactory().buildOrmXml(this);
		ormXml.initialize(ormResource);
		return ormXml;
	}
	
	
	// *************************************************************************
	
	public PersistenceUnitDefaults persistenceUnitDefaults() {
		if (getOrmXml() != null) {
			return getOrmXml().persistenceUnitDefaults();
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
		if (xmlMappingFileRef == null) {
			return false;
		}
		return xmlMappingFileRef.containsOffset(textOffset);
	}
	
	public TextRange selectionTextRange() {
		if (isVirtual()) {
			return null;
		}
		return xmlMappingFileRef.selectionTextRange();
	}
	
	public TextRange validationTextRange() {
		if (isVirtual()) {
			return persistenceUnit().validationTextRange();
		}
		return xmlMappingFileRef.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getFileName());
	}
}
