package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import java.util.Iterator;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

/**
 * A new {@link Command} class that is called to add the {@link PersistentType}
 * to the XML mapping file, specified in the project's Properties page.
 * 
 * @author i045693
 * 
 */
public class AddPersistentTypeToOrmXmlCommand implements Command {

	private JpaProject jpaProject;
	private String mapping;
	private String persistentTypeName;
	
	/**
	 * Constructor for the add Persistent type to the mapping file command.
	 * @param jpaProject - the project, which the persistent type will be created
	 * @param mapping - the mapping of the persistent type
	 * @param persistentTypeName - the persistent type to be added
	 */
	public AddPersistentTypeToOrmXmlCommand(JpaProject jpaProject, String mapping, String persistentTypeName) {
		super();
		this.jpaProject = jpaProject;
		this.persistentTypeName = persistentTypeName;
		this.mapping = mapping;
	}

	/**
	 * Adds the given persistent type to the specified mapping file.
	 */
	public void execute() {
		PersistenceUnit unit = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		if(unit.getMappingFileRefsSize() == 0)
			return;

	    String ormFileName = JPADiagramPropertyPage.getOrmXmlFileName(jpaProject.getProject());	    
	    Iterator<MappingFileRef> iter = unit.getMappingFileRefs().iterator();
	    while(iter.hasNext()){
	    	MappingFileRef mapFile = iter.next();
	    	if(mapFile.getFileName().equals(ormFileName)){
	    		OrmXml ormXml = (OrmXml) mapFile.getMappingFile();
	    		OrmPersistentType type = ormXml.getRoot().addPersistentType(mapping, persistentTypeName);
	    	    for(PersistentAttribute pa : type.getDefaultAttributes()){
	    	    	type.addAttributeToXml(type.getAttributeNamed(pa.getName()));
	    	    }
	    	}
	    }
	}
	
}
