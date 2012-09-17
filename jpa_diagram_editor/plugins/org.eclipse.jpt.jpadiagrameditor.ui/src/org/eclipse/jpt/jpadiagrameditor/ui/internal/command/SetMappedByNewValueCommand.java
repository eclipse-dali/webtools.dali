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
	private IRelation rel;
	
	public SetMappedByNewValueCommand(IJPAEditorFeatureProvider fp,
			PersistenceUnit pu, String inverseEntityName,
			String inverseAttributeName, JavaPersistentAttribute newAt,
			IRelation rel) {
		super();
		this.fp =fp;
		this.pu = pu;
		this.inverseEntityName = inverseEntityName;
		this.inverseAttributeName = inverseAttributeName;
		this.newAt = newAt;
		this.rel = rel;
	}

	public void execute() {
		fp.addAttribForUpdate(pu, inverseEntityName
				+ EntityChangeListener.SEPARATOR + inverseAttributeName
				+ EntityChangeListener.SEPARATOR + newAt.getName());
		Annotation a = rel.getInverseAnnotatedAttribute().getMapping().getMappingAnnotation();
		if (OwnableRelationshipMappingAnnotation.class.isInstance(a)) {
			((OwnableRelationshipMappingAnnotation)a).setMappedBy(newAt.getName());
		
			rel.getInverse().getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		}
	}
	
}
