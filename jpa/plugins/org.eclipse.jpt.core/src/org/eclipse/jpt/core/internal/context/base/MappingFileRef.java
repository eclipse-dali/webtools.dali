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
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;

public class MappingFileRef extends JpaContextNode 
	implements IMappingFileRef
{
	protected XmlMappingFileRef xmlMappingFileRef;
	
	protected String fileName;
	
	protected OrmXml ormXml;
	
	public MappingFileRef(IPersistenceUnit parent) {
		super(parent);
	}
	
	public XmlPersistentType persistentTypeFor(String fullyQualifiedTypeName) {
		if (getOrmXml() != null) {
			return getOrmXml().persistentTypeFor(fullyQualifiedTypeName);
		}
		return null;
	}
	
	// **************** file name **********************************************
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String newFileName) {
		String oldFileName = this.fileName;
		this.fileName = newFileName;
		this.xmlMappingFileRef.setFileName(newFileName);
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

	public PersistenceUnitDefaults persistenceUnitDefaults() {
		if (getOrmXml() != null) {
			return getOrmXml().persistenceUnitDefaults();
		}
		return null;
	}
	
	// **************** updating ***********************************************
	
	public void initialize(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		this.fileName = mappingFileRef.getFileName();
		
		if (this.fileName != null) {
			OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(jpaProject().project());
			OrmResource ormResource = oae.getResource(this.fileName);
			
			if (ormResource != null && ormResource.exists()) {
				this.ormXml = createOrmXml(ormResource);
			}
			oae.dispose();
		}
	}
	
	public void update(XmlMappingFileRef mappingFileRef) {
		this.xmlMappingFileRef = mappingFileRef;
		setFileName(mappingFileRef.getFileName());
		if (this.fileName != null) {
			OrmArtifactEdit oae = OrmArtifactEdit.getArtifactEditForRead(jpaProject().project());
			OrmResource ormResource = oae.getResource(this.fileName);
			if (ormResource != null && ormResource.exists()) {
				if (this.ormXml != null) {
					this.ormXml.update(ormResource);
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
		OrmXml ormXml = jpaFactory().createOrmXml(this);
		ormXml.initialize(ormResource);
		return ormXml;
	}

	// *************************************************************************
	
	public ITextRange validationTextRange() {
		return this.xmlMappingFileRef.validationTextRange();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getFileName());
	}
}
