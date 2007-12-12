/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.jface;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.internal.jface.StructuredContentProviderAdapter;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * an example UI for testing various permutations of the
 * StructuredContentProviderAdapter
 */
public class StructuredContentProviderAdapterUITest
	extends ApplicationWindow
{
	private WritablePropertyValueModel taskListHolder;
	private Text taskTextField;

	public static void main(String[] args) throws Exception {
		Window window = new StructuredContentProviderAdapterUITest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private StructuredContentProviderAdapterUITest(String[] args) {
		super(null);
		this.taskListHolder = new SimplePropertyValueModel(new TaskList());
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(ClassTools.shortClassNameForObject(this));
		parent.setSize(800, 400);
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new FormLayout());
		Control taskListPanel = this.buildTaskListPanel(mainPanel);
		this.buildControlPanel(mainPanel, taskListPanel);
		return mainPanel;
	}

	private Control buildTaskListPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100, -30);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		this.buildPrimitiveTaskListPanel(panel);
		this.buildDisplayableTaskListPanel(panel);

		return panel;
	}

	private void buildPrimitiveTaskListPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(50);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildUnsortedPrimitiveListPanel(panel);
		this.buildStandardSortedPrimitiveListPanel(panel);
		this.buildCustomSortedPrimitiveListPanel(panel);
	}

	private void buildDisplayableTaskListPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);

		FormData fd = new FormData();
			fd.top = new FormAttachment(50);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildUnsortedDisplayableListPanel(panel);
		this.buildStandardSortedDisplayableListPanel(panel);
		this.buildCustomSortedDisplayableListPanel(panel);
	}

	private void buildUnsortedPrimitiveListPanel(Composite parent) {
		this.buildListPanel(parent, "primitive unsorted", this.buildUnsortedPrimitiveListModel());
	}

	private void buildStandardSortedPrimitiveListPanel(Composite parent) {
		this.buildListPanel(parent, "primitive sorted", this.buildStandardSortedPrimitiveListModel());
	}

	private void buildCustomSortedPrimitiveListPanel(Composite parent) {
		this.buildListPanel(parent, "primitive reverse sorted", this.buildCustomSortedPrimitiveListModel());
	}

	private void buildUnsortedDisplayableListPanel(Composite parent) {
		this.buildListPanel(parent, "displayable unsorted", this.buildUnsortedDisplayableListModel());
	}

	private void buildStandardSortedDisplayableListPanel(Composite parent) {
		this.buildListPanel(parent, "displayable sorted", this.buildStandardSortedDisplayableListModel());
	}

	private void buildCustomSortedDisplayableListPanel(Composite parent) {
		this.buildListPanel(parent, "displayable reverse sorted", this.buildCustomSortedDisplayableListModel());
	}

	private ListValueModel buildUnsortedPrimitiveListModel() {
		return this.buildPrimitiveTaskListAdapter();
	}

	private ListValueModel buildStandardSortedPrimitiveListModel() {
		return new SortedListValueModelAdapter(this.buildPrimitiveTaskListAdapter());
	}

	private ListValueModel buildCustomSortedPrimitiveListModel() {
		return new SortedListValueModelAdapter(this.buildPrimitiveTaskListAdapter(), this.buildCustomStringComparator());
	}

	private ListValueModel buildUnsortedDisplayableListModel() {
		return this.buildDisplayableTaskListAdapter();
	}

	private ListValueModel buildStandardSortedDisplayableListModel() {
		return new SortedListValueModelAdapter(this.buildDisplayableTaskListAdapter());
	}

	private ListValueModel buildCustomSortedDisplayableListModel() {
		return new SortedListValueModelAdapter(this.buildDisplayableTaskListAdapter(), this.buildCustomTaskObjectComparator());
	}

	private void buildListPanel(Composite parent, String label, ListValueModel model) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayout(new FormLayout());

		Label listLabel = new Label(panel, SWT.LEFT | SWT.VERTICAL);
		listLabel.setText(label);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0, 3);
			fd.bottom = new FormAttachment(0, 20);
			fd.left = new FormAttachment(0, 5);
			fd.right = new FormAttachment(100);
		listLabel.setLayoutData(fd);

		ListViewer listViewer = new ListViewer(panel);
		fd = new FormData();
			fd.top = new FormAttachment(listLabel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		listViewer.getList().setLayoutData(fd);
		StructuredContentProviderAdapter.adapt(listViewer, model);
	}

	private Comparator<String> buildCustomStringComparator() {
		return new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s2.compareTo(s1);
			}
		};
	}

	private Comparator<TaskObject> buildCustomTaskObjectComparator() {
		return new Comparator<TaskObject>() {
			public int compare(TaskObject to1, TaskObject to2) {
				return to2.compareTo(to1);
			}
		};
	}

	private ListValueModel buildPrimitiveTaskListAdapter() {
		return new ListAspectAdapter(TaskList.TASKS_LIST, this.taskList()) {
			@Override
			protected ListIterator<String> listIterator_() {
				return ((TaskList) this.subject).tasks();
			}
		};
	}

	private ListValueModel buildDisplayableTaskListAdapter() {
		return new ListAspectAdapter(TaskList.TASK_OBJECTS_LIST, this.taskList()) {
			@Override
			protected ListIterator<TaskObject> listIterator_() {
				return ((TaskList) this.subject).taskObjects();
			}
		};
	}

	private void buildControlPanel(Composite parent, Control taskListPanel) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(taskListPanel);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		Control clearButton = this.buildClearButton(panel);
		this.buildAddRemoveTaskPanel(panel, clearButton);
	}

	// is there a better way to associate an ACI with form data?
	private Control buildClearButton(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(100, -50);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildClearACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildClearACI() {
		Action action = new Action("clear", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				StructuredContentProviderAdapterUITest.this.clearTasks();
			}
		};
		action.setToolTipText("clear all the tasks");
		return new ActionContributionItem(action);
	}

	private void buildAddRemoveTaskPanel(Composite parent, Control clearButton) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(clearButton);
		panel.setLayoutData(fd);

		panel.setLayout(new FormLayout());
		Control addButton = this.buildAddButton(panel);
		Control removeButton = this.buildRemoveButton(panel);
		this.buildTaskTextField(panel, addButton, removeButton);
	}

	// is there a better way to associate an ACI with form data?
	private Control buildAddButton(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(0);
			fd.right = new FormAttachment(0, 50);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildAddACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildAddACI() {
		Action action = new Action("add", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				StructuredContentProviderAdapterUITest.this.addTask();
			}
		};
		action.setToolTipText("add a task with the name in the entry field");
		return new ActionContributionItem(action);
	}

	// is there a better way to associate an ACI with form data?
	private Control buildRemoveButton(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(100, -50);
			fd.right = new FormAttachment(100);
		panel.setLayoutData(fd);

		panel.setLayout(new FillLayout());
		this.buildRemoveACI().fill(panel);
		return panel;
	}

	private ActionContributionItem buildRemoveACI() {
		Action action = new Action("remove", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				StructuredContentProviderAdapterUITest.this.removeTask();
			}
		};
		action.setToolTipText("remove the task with the name in the entry field");
		return new ActionContributionItem(action);
	}

	private void buildTaskTextField(Composite parent, Control addButton, Control removeButton) {
		this.taskTextField = new Text(parent, SWT.SINGLE | SWT.BORDER);
		FormData fd = new FormData();
			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);
			fd.left = new FormAttachment(addButton);
			fd.right = new FormAttachment(removeButton);
		this.taskTextField.setLayoutData(fd);
	}

	private String taskTextFieldText() {
		return this.taskTextField.getText();
	}

	private TaskList taskList() {
		return (TaskList) this.taskListHolder.value();
	}

	void addTask() {
		String taskText = this.taskTextFieldText();
		if (taskText.length() != 0) {
			this.taskList().addTask(taskText);
		}
	}

	void removeTask() {
		String task = this.taskTextFieldText();
		if (task.length() != 0) {
			this.taskList().removeTask(task);
		}
	}

	void clearTasks() {
		this.taskList().clearTasks();
	}


	// ********** TaskList **********

	private class TaskList extends AbstractModel {
		private List<String> tasks = new ArrayList<String>();
		private List<TaskObject> taskObjects = new ArrayList<TaskObject>();
		public static final String TASKS_LIST = "tasks";
		public static final String TASK_OBJECTS_LIST = "taskObjects";
		TaskList() {
			super();
		}
		public ListIterator<String> tasks() {
			return this.tasks.listIterator();
		}
		public ListIterator<TaskObject> taskObjects() {
			return this.taskObjects.listIterator();
		}
		public void addTask(String task) {
			int index = this.tasks.size();
			this.tasks.add(index, task);
			this.fireItemAdded(TASKS_LIST, index, task);
	
			TaskObject taskObject = new TaskObject(task);
			this.taskObjects.add(index, taskObject);
			this.fireItemAdded(TASK_OBJECTS_LIST, index, taskObject);
		}		
		public void removeTask(String task) {
			int index = this.tasks.indexOf(task);
			if (index != -1) {
				Object removedTask = this.tasks.remove(index);
				this.fireItemRemoved(TASKS_LIST, index, removedTask);
				// assume the indexes match...
				Object removedTaskObject = this.taskObjects.remove(index);
				this.fireItemRemoved(TASK_OBJECTS_LIST, index, removedTaskObject);
			}
		}
		public void clearTasks() {
			this.tasks.clear();
			this.fireListCleared(TASKS_LIST);
			this.taskObjects.clear();
			this.fireListCleared(TASK_OBJECTS_LIST);
		}
	}


	// ********** TaskObject **********

	private class TaskObject extends AbstractModel implements Displayable {
		private String name;
		private Date creationTimeStamp;
		public TaskObject(String name) {
			this.name = name;
			this.creationTimeStamp = new Date();
		}
		public String displayString() {
			return this.name + ": " + this.creationTimeStamp.getTime();
		}
		public int compareTo(Displayable o) {
			return DEFAULT_COMPARATOR.compare(this, o);
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(DISPLAY_STRING_PROPERTY, old, name);
		}
		@Override
		public String toString() {
			return "TaskObject(" + this.displayString() + ")";
		}
	}

	public interface Displayable extends Model, Comparable<Displayable> {
	
		String displayString();
			String DISPLAY_STRING_PROPERTY = "displayString";
	
	
		// ********** helper implementations **********
	
		Collator DEFAULT_COLLATOR = Collator.getInstance();
	
		Comparator<Displayable> DEFAULT_COMPARATOR =
			new Comparator<Displayable>() {
				public int compare(Displayable d1, Displayable d2) {
					// disallow duplicates based on object identity
					if (d1 == d2) {
						return 0;
					}
	
					// first compare display strings using the default collator
					int result = DEFAULT_COLLATOR.compare(d1.displayString(), d2.displayString());
					if (result != 0) {
						return result;
					}
	
					// then compare using object-id
					result = System.identityHashCode(d1) - System.identityHashCode(d2);
					if (result != 0) {
						return result;
					}
	
					// It's unlikely that we get to this point; but, just in case, we will return -1.
					// Unfortunately, this introduces some mild unpredictability to the sort order
					// (unless the objects are always passed into this method in the same order).
					return -1;		// if all else fails, indicate that o1 < o2
				}
				@Override
				public String toString() {
					return "Displayable.DEFAULT_COMPARATOR";
				}
			};
	
	}
}
