package org.eclipse.jpt.jpadiagrameditor.ui.internal.command;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.EntityChangeListener;

public class SetMappedByNewValueCommand implements Command {
	
	private IJPAEditorFeatureProvider fp;
	private PersistenceUnit pu;
	private String inverseEntityName;
	private String inverseAttributeName;
	private JavaPersistentAttribute newAt;
	private JavaPersistentAttribute oldAt;
	private IRelation rel;
	
	public SetMappedByNewValueCommand(IJPAEditorFeatureProvider fp,
			PersistenceUnit pu, String inverseEntityName,
			String inverseAttributeName, JavaPersistentAttribute newAt,
			JavaPersistentAttribute oldAt,
			IRelation rel) {
		super();
		this.fp =fp;
		this.pu = pu;
		this.inverseEntityName = inverseEntityName;
		this.inverseAttributeName = inverseAttributeName;
		this.newAt = newAt;
		this.oldAt = oldAt;
		this.rel = rel;
	}

	public void execute() {
		fp.addAttribForUpdate(pu, inverseEntityName
				+ EntityChangeListener.SEPARATOR + inverseAttributeName
				+ EntityChangeListener.SEPARATOR + newAt.getName() 
				+ EntityChangeListener.SEPARATOR + oldAt.getName());
		Annotation a = rel.getInverseAnnotatedAttribute().getMapping().getMappingAnnotation();
		if (OwnableRelationshipMappingAnnotation.class.isInstance(a)) {
			String mappedBy = ((OwnableRelationshipMappingAnnotation)a).getMappedBy();
			String[] mappedByAttrs = mappedBy.split("\\."); //$NON-NLS-1$
			if(mappedByAttrs.length > 1){
				if(mappedByAttrs[0].equals(oldAt.getName())){
					mappedBy = newAt.getName() + "." + mappedByAttrs[1]; //$NON-NLS-1$
				} else if(mappedByAttrs[1].equals(oldAt.getName())){
					mappedBy = mappedByAttrs[0] + "." + newAt.getName(); //$NON-NLS-1$
				}
			} else {
				mappedBy = newAt.getName();
			}
			((OwnableRelationshipMappingAnnotation)a).setMappedBy(mappedBy);
		
			rel.getInverse().getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		}
	}
	
}
