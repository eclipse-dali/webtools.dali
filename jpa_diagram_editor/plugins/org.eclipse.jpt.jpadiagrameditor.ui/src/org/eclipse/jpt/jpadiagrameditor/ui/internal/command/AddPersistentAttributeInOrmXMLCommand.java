package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class AddPersistentAttributeInOrmXMLCommand implements Command {
	
	private PersistentType jpt;
	private PersistentAttribute jpa;
	private String mappingKey;
	
	public AddPersistentAttributeInOrmXMLCommand(PersistentType jpt, PersistentAttribute jpa, String mappingKey){
		this.jpt = jpt;
		this.jpa = jpa;
		this.mappingKey = mappingKey;
	}	

	public void execute() {
		MappingFileRef ormXml = JpaArtifactFactory.instance().getOrmXmlByForPersistentType(jpt);
		if(ormXml != null && ormXml.getMappingFile() != null) {
			OrmPersistentType ormPersistentType = (OrmPersistentType)ormXml.getMappingFile().getManagedType(jpt.getName());
			if(ormPersistentType == null)
				return;
			OrmPersistentAttribute ormAttribute = ormPersistentType.getAttributeNamed(jpa.getName());
			if(ormAttribute == null) {
				ormPersistentType.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
				ormPersistentType.update();
				ormAttribute = ormPersistentType.getAttributeNamed(jpa.getName());
			}
			if(ormAttribute != null && ormAttribute.isVirtual()){
				if(mappingKey == null){
					ormPersistentType.addAttributeToXml(ormAttribute);
				} else {
					ormPersistentType.addAttributeToXml(ormAttribute, mappingKey);
				}
			}
		}
		
		jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		jpt.synchronizeWithResourceModel();
		jpt.update();
	}

}
