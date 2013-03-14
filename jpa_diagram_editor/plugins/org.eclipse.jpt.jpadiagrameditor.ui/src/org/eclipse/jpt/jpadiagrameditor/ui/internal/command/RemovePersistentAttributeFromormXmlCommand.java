package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class RemovePersistentAttributeFromormXmlCommand implements Command {

	private PersistentType jpt;
	private String attributeName;
	
	public RemovePersistentAttributeFromormXmlCommand(PersistentType jpt, String attributeName){
		this.jpt = jpt;
		this.attributeName = attributeName;
	}

	public void execute() {
		MappingFileRef ormXml = JpaArtifactFactory.instance().getOrmXmlByForPersistentType(jpt);
		if(ormXml != null && ormXml.getMappingFile() != null) {
			OrmPersistentType ormPersistentType = (OrmPersistentType)ormXml.getMappingFile().getPersistentType(jpt.getName());
			OrmPersistentAttribute ormReadOnlyAttribute = ormPersistentType.getAttributeNamed(attributeName);
			if(ormReadOnlyAttribute instanceof OrmSpecifiedPersistentAttribute)
				ormPersistentType.removeAttributeFromXml((OrmSpecifiedPersistentAttribute) ormReadOnlyAttribute);
		}
		jpt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		jpt.synchronizeWithResourceModel();
		jpt.update();
	}
}
