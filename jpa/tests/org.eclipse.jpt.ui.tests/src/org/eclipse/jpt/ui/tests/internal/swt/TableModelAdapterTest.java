/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.swt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter.SelectionChangeEvent;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter.SelectionChangeListener;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public class TableModelAdapterTest {

	private Shell shell;
	private WritablePropertyValueModel<Manager> subjectHolder;

	private ColumnAdapter<Employee> buildColumnAdapter() {
		return new TableColumnAdapter();
	}

	private WritablePropertyValueModel<Employee> buildEmployeeHolder() {
		return new SimplePropertyValueModel<Employee>();
	}

	private SimpleCollectionValueModel<Employee> buildEmployeeHolders() {
		return new SimpleCollectionValueModel<Employee>();
	}

	private ListValueModel<Employee> buildEmployeeListHolder() {
		return new ListAspectAdapter<Manager, Employee>(subjectHolder, Manager.EMPLOYEES_LIST) {
			@Override
			protected ListIterator<Employee> listIterator_() {
				return subject.employees();
			}

			@Override
			protected int size_() {
				return subject.employeesSize();
			}
		};
	}

	private ITableLabelProvider buildLabelProvider() {
		return new TableLabelProvider();
	}

	private SelectionChangeListener<Employee> buildSelectionChangeListener(final Collection<Employee> employees) {
		return new SelectionChangeListener<Employee>() {
			public void selectionChanged(SelectionChangeEvent<Employee> e) {
				employees.clear();
				CollectionTools.addAll(employees, e.selection());
			}
		};
	}

	private WritablePropertyValueModel<Manager> buildSubjectHolder() {
		return new SimplePropertyValueModel<Manager>();
	}

	@Before
	public void setUp() throws Exception {

		shell         = new Shell(Display.getCurrent());
		subjectHolder = buildSubjectHolder();
	}

	@After
	public void tearDown() throws Exception {

		if (!shell.isDisposed()) {
			shell.dispose();
		}

		shell         = null;
		subjectHolder = null;
	}

	@Test
	public void testChanged() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Make sure the Table was populated
		Assert.assertEquals("The number of TableItems should be 3", 3, table.getItemCount());

		// Change the list of Employees
		ArrayList<Employee> employees = new ArrayList<Employee>(3);
		employees.add(employee3);
		employees.add(employee2);
		employees.add(employee1);
		manager.changeEmployees(employees);

		Assert.assertEquals("The number of TableItems should be 3", 3, table.getItemCount());

		testTableItemProperties(table, 0, expectedName3, expectedManager3, expectedTitle3);
		testTableItemProperties(table, 1, expectedName2, expectedManager2, expectedTitle2);
		testTableItemProperties(table, 2, expectedName1, expectedManager1, expectedTitle1);
	}

	@Test
	public void testCleared() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Make sure the Table was populated
		Assert.assertEquals("The number of TableItems should be 3", 3, table.getItemCount());

		// Test removing them all
		manager.removeAllEmployees();

		Assert.assertEquals("The list holder should have been cleared", 0, listHolder.size());
		Assert.assertEquals("The Table should have been cleared", 0, table.getItemCount());
	}

	@Test
	public void testItemAdded() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create a new Employee
		String expectedName    = "Dali";
		String expectedManager = "WTP";
		String expectedTitle   = "plug-in";

		Employee employee = manager.addEmployee(expectedName, expectedTitle, expectedManager);
		Assert.assertNotNull("The new Employee was not created", employee);

		// Retrieve the TableItem representing the new Employee
		int index = tableModel.indexOf(employee);
		Assert.assertEquals("The new Employee was not added to the table model", 0, index);
		Assert.assertEquals("The number of TableItem should be 1", 1, table.getItemCount());

		testTableItemProperties(table, index, expectedName, expectedManager, expectedTitle);
	}

	@Test
	public void testItemMoved() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Make sure the Employees were added to the Table
		Assert.assertEquals("The number of TableItem should be 3", 3, table.getItemCount());

		// Move an Employee up the list
		manager.moveEmployeeUp(employee2);

		int index1 = tableModel.indexOf(employee1);
		Assert.assertEquals("The Employee 1 was not moved in the table model", 1, index1);

		int index2 = tableModel.indexOf(employee2);
		Assert.assertEquals("The Employee 2 was not moved in the table model", 0, index2);

		int index3 = tableModel.indexOf(employee3);
		Assert.assertEquals("The Employee 3 should not have been moved in the table model", 2, index3);

		testTableItemProperties(table, index1, expectedName1, expectedManager1, expectedTitle1);
		testTableItemProperties(table, index2, expectedName2, expectedManager2, expectedTitle2);
		testTableItemProperties(table, index3, expectedName3, expectedManager3, expectedTitle3);

		// Move an Employee down the list
		manager.moveEmployeeDown(employee1);

		index1 = tableModel.indexOf(employee1);
		Assert.assertEquals("The Employee 1 should not have been moved in the table model", 2, index1);

		index2 = tableModel.indexOf(employee2);
		Assert.assertEquals("The Employee 2 was not moved in the table model", 0, index2);

		index3 = tableModel.indexOf(employee3);
		Assert.assertEquals("The Employee 3 was not moved in the table model", 1, index3);

		testTableItemProperties(table, index1, expectedName1, expectedManager1, expectedTitle1);
		testTableItemProperties(table, index2, expectedName2, expectedManager2, expectedTitle2);
		testTableItemProperties(table, index3, expectedName3, expectedManager3, expectedTitle3);
	}

	@Test
	public void testItemRemoved() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create a new Employee
		String expectedName    = "Dali";
		String expectedManager = "WTP";
		String expectedTitle   = "plug-in";

		Employee employee = manager.addEmployee(expectedName, expectedTitle, expectedManager);
		Assert.assertNotNull("The new Employee was not created", employee);
		Assert.assertEquals("The number of TableItem should be 1", 1, table.getItemCount());

		// Make sure it was added to the model
		int index = tableModel.indexOf(employee);
		Assert.assertEquals("The new Employee was not added to the table model", 0, index);
		testTableItemProperties(table, index, expectedName, expectedManager, expectedTitle);

		// Retrieve the TableItem representing the new Employee
		TableItem tableItem = table.getItem(index);
		Assert.assertNotNull("No TableItem was found for the new Employee", tableItem);

		// Now test the item being removed
		manager.removeEmployee(employee);
		index = tableModel.indexOf(employee);
		Assert.assertEquals("The Employee was not removed from the table model", -1, index);
		Assert.assertEquals("The number of TableItem should be 0", 0, table.getItemCount());
	}

	@Test
	public void testItemReplaced() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create a new Employee
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		Employee employee1 = manager.addEmployee(expectedName1, expectedTitle1, expectedManager1);
		Assert.assertNotNull("The new Employee was not created", employee1);

		// Make sure it was added to the model
		int index1 = tableModel.indexOf(employee1);
		Assert.assertEquals("The new Employee was not added to the table model", 0, index1);
		Assert.assertEquals("The number of TableItem should be 1", 1, table.getItemCount());

		// Retrieve the TableItem representing the new Employee
		TableItem tableItem = table.getItem(index1);
		Assert.assertNotNull("No TableItem was found for the new Employee", tableItem);

		testTableItemProperties(table, index1, expectedName1, expectedManager1, expectedTitle1);

		// Replace the Employee
		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		manager.replace(employee1, employee2);

		int index2 = tableModel.indexOf(employee2);
		Assert.assertSame("The Employee that got replaced should be at index " + index1, index1, index2);
		Assert.assertEquals("The number of TableItem should be 1", 1, table.getItemCount());

		testTableItemProperties(table, index2, expectedName2, expectedManager2, expectedTitle2);
	}

	@Test
	public void testItemsAdded() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Retrieve the TableItems representing the employees
		Assert.assertEquals("The number of TableItem should be 3", 3, table.getItemCount());

		int index = tableModel.indexOf(employee1);
		Assert.assertEquals("The Employee 1 was not added to the table model", 0, index);

		index = tableModel.indexOf(employee2);
		Assert.assertEquals("The Employee 2 was not added to the table model", 1, index);

		index = tableModel.indexOf(employee3);
		Assert.assertEquals("The Employee 3 was not added to the table model", 2, index);

		// Make sure the TableItem was correctly populated
		testTableItemProperties(table, 0, expectedName1, expectedManager1, expectedTitle1);
		testTableItemProperties(table, 1, expectedName2, expectedManager2, expectedTitle2);
		testTableItemProperties(table, 2, expectedName3, expectedManager3, expectedTitle3);
	}

	@Test
	public void testItemsMoved() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		String expectedName4    = "Dali_4";
		String expectedManager4 = "WTP_4";
		String expectedTitle4   = "plug-in_4";

		String expectedName5    = "Dali_5";
		String expectedManager5 = "WTP_5";
		String expectedTitle5   = "plug-in_5";

		String expectedName6    = "Dali_6";
		String expectedManager6 = "WTP_6";
		String expectedTitle6   = "plug-in_6";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);
		Employee employee4 = new Employee(expectedName4, expectedTitle4, expectedManager4);
		Employee employee5 = new Employee(expectedName5, expectedTitle5, expectedManager5);
		Employee employee6 = new Employee(expectedName6, expectedTitle6, expectedManager6);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);
		manager.addEmployee(employee4);
		manager.addEmployee(employee5);
		manager.addEmployee(employee6);

		// Make sure the Employees were added to the Table
		Assert.assertEquals("The number of TableItem should be 6", 6, table.getItemCount());

		// Move an Employee up the list
		ArrayList<Employee> employees = new ArrayList<Employee>(3);
		employees.add(employee3);
		employees.add(employee4);
		employees.add(employee5);
		manager.moveEmployees(employees, 0);

		int index1 = tableModel.indexOf(employee1);
		int index2 = tableModel.indexOf(employee2);
		int index3 = tableModel.indexOf(employee3);
		int index4 = tableModel.indexOf(employee4);
		int index5 = tableModel.indexOf(employee5);
		int index6 = tableModel.indexOf(employee6);

		Assert.assertEquals("The Employee 1 is not at the right index", 3, index1);
		Assert.assertEquals("The Employee 2 is not at the right index", 4, index2);
		Assert.assertEquals("The Employee 3 is not at the right index", 0, index3);
		Assert.assertEquals("The Employee 4 is not at the right index", 1, index4);
		Assert.assertEquals("The Employee 5 is not at the right index", 2, index5);
		Assert.assertEquals("The Employee 6 is not at the right index", 5, index6);

		testTableItemProperties(table, index1, expectedName1, expectedManager1, expectedTitle1);
		testTableItemProperties(table, index2, expectedName2, expectedManager2, expectedTitle2);
		testTableItemProperties(table, index3, expectedName3, expectedManager3, expectedTitle3);
		testTableItemProperties(table, index4, expectedName4, expectedManager4, expectedTitle4);
		testTableItemProperties(table, index5, expectedName5, expectedManager5, expectedTitle5);
		testTableItemProperties(table, index6, expectedName6, expectedManager6, expectedTitle6);

		// Move an Employee down the list
		employees = new ArrayList<Employee>(2);
		employees.add(employee1);
		employees.add(employee2);
		manager.moveEmployees(employees, 4);

		index1 = tableModel.indexOf(employee1);
		index2 = tableModel.indexOf(employee2);
		index3 = tableModel.indexOf(employee3);
		index4 = tableModel.indexOf(employee4);
		index5 = tableModel.indexOf(employee5);
		index6 = tableModel.indexOf(employee6);

		Assert.assertEquals("The Employee 1 is not at the right index", 4, index1);
		Assert.assertEquals("The Employee 2 is not at the right index", 5, index2);
		Assert.assertEquals("The Employee 3 is not at the right index", 0, index3);
		Assert.assertEquals("The Employee 4 is not at the right index", 1, index4);
		Assert.assertEquals("The Employee 5 is not at the right index", 2, index5);
		Assert.assertEquals("The Employee 6 is not at the right index", 3, index6);

		testTableItemProperties(table, index1, expectedName1, expectedManager1, expectedTitle1);
		testTableItemProperties(table, index2, expectedName2, expectedManager2, expectedTitle2);
		testTableItemProperties(table, index3, expectedName3, expectedManager3, expectedTitle3);
		testTableItemProperties(table, index4, expectedName4, expectedManager4, expectedTitle4);
		testTableItemProperties(table, index5, expectedName5, expectedManager5, expectedTitle5);
		testTableItemProperties(table, index6, expectedName6, expectedManager6, expectedTitle6);
	}

	@Test
	public void testItemsRemoved() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Retrieve the TableItems representing the employees
		Assert.assertEquals("The number of TableItem should be 3", 3, table.getItemCount());

		int index = tableModel.indexOf(employee1);
		Assert.assertEquals("The Employee 1 was not added to the table model", 0, index);

		index = tableModel.indexOf(employee2);
		Assert.assertEquals("The Employee 2 was not added to the table model", 1, index);

		index = tableModel.indexOf(employee3);
		Assert.assertEquals("The Employee 3 was not added to the table model", 2, index);

		// Remove 2 items
		ArrayList<Employee> employees = new ArrayList<Employee>(2);
		employees.add(employee1);
		employees.add(employee3);
		manager.removeEmployees(employees);

		Assert.assertEquals("The number of TableItem should be 1", 1, table.getItemCount());
	}

	@Test
	public void testPropertyChanged() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		WritablePropertyValueModel<Employee> selectedItemHolder = buildEmployeeHolder();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create a new Employee
		String expectedName    = "Dali";
		String expectedManager = "WTP";
		String expectedTitle   = "plug-in";

		Employee employee = manager.addEmployee(expectedName, expectedTitle, expectedManager);
		Assert.assertNotNull("The new Employee was not created", employee);

		// Make sure it was added to the model
		int index = tableModel.indexOf(employee);
		Assert.assertEquals("The new Employee was not added to the table model", 0, index);
		Assert.assertEquals("The number of TableItem should be 1", 1, table.getItemCount());

		// Retrieve the TableItem representing the new Employee
		TableItem tableItem = table.getItem(index);
		Assert.assertNotNull("No TableItem was found for the new Employee", tableItem);

		// Name property
		String actualName = tableItem.getText(TableColumnAdapter.NAME_COLUMN);
		Assert.assertEquals("TableItem[NAME_COLUMN] was not set correctly", expectedName, actualName);

		expectedName = "Jpt";
		employee.setName(expectedName);

		actualName = tableItem.getText(TableColumnAdapter.NAME_COLUMN);
		Assert.assertEquals("TableItem[NAME_COLUMN] was not set correctly", expectedName, actualName);

		// Manager property
		String actualManager = tableItem.getText(TableColumnAdapter.MANAGER_COLUMN);
		Assert.assertEquals("TableItem[MANAGER_COLUMN] was not set correctly", expectedManager, actualManager);

		expectedManager = "boss";
		employee.setManager(expectedManager);

		actualManager = tableItem.getText(TableColumnAdapter.MANAGER_COLUMN);
		Assert.assertEquals("TableItem[MANAGER_COLUMN] was not set correctly", expectedManager, actualManager);

		// Title property
		String actualTitle = tableItem.getText(TableColumnAdapter.TITLE_COLUMN);
		Assert.assertEquals("TableItem[TITLE_COLUMN] was not set correctly", expectedTitle, actualTitle);

		expectedTitle = "EclipseLink";
		employee.setTitle(expectedTitle);

		actualTitle = tableItem.getText(TableColumnAdapter.TITLE_COLUMN);
		Assert.assertEquals("TableItem[TITLE_COLUMN] was not set correctly", expectedTitle, actualTitle);
	}

	@Test
	public void testSelectedItemsAddedRemoved() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		SimpleCollectionValueModel<Employee> selectedItemsHolder = buildEmployeeHolders();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemsHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Test adding selected items
		ArrayList<Employee> employees = new ArrayList<Employee>(2);
		employees.add(employee1);
		employees.add(employee3);
		selectedItemsHolder.addAll(employees);

		Collection<Employee> actualEmployees = tableModel.selectedItems();
		Assert.assertNotNull("The collection of selected items can't be null", actualEmployees);
		Assert.assertEquals("The count of selected items should be 2", 2, actualEmployees.size());

		actualEmployees.remove(employee1);
		actualEmployees.remove(employee3);

		Assert.assertTrue("The selected items was not retrieved correctly", actualEmployees.isEmpty());

		// Test removing selected items
		selectedItemsHolder.remove(employee1);

		actualEmployees = tableModel.selectedItems();
		Assert.assertNotNull("The collection of selected items can't be null", actualEmployees);
		Assert.assertEquals("The count of selected items should be 1", 1, actualEmployees.size());

		actualEmployees.remove(employee3);

		Assert.assertTrue("The selected items was not retrieved correctly", actualEmployees.isEmpty());
	}

	@Test
	public void testSelectedItemsCleared() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		ListValueModel<Employee> listHolder = buildEmployeeListHolder();
		SimpleCollectionValueModel<Employee> selectedItemsHolder = buildEmployeeHolders();

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			listHolder,
			selectedItemsHolder,
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Test adding selected items
		ArrayList<Employee> employees = new ArrayList<Employee>(2);
		employees.add(employee1);
		employees.add(employee3);
		selectedItemsHolder.addAll(employees);

		Collection<Employee> actualEmployees = tableModel.selectedItems();
		Assert.assertNotNull("The collection of selected items can't be null", actualEmployees);
		Assert.assertEquals("The count of selected items should be 2", 2, actualEmployees.size());

		actualEmployees.remove(employee1);
		actualEmployees.remove(employee3);
		Assert.assertTrue("The selected items was not retrieved correctly", actualEmployees.isEmpty());

		// Test removing selected items
		selectedItemsHolder.clear();

		actualEmployees = tableModel.selectedItems();
		Assert.assertNotNull("The collection of selected items can't be null", actualEmployees);
		Assert.assertEquals("The count of selected items should be ", 0, actualEmployees.size());
	}

	@Test
	public void testSelectionChangeListener() {

		Table table = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		Manager manager = new Manager();
		subjectHolder.setValue(manager);

		TableModel tableModel = new TableModel(
			buildEmployeeListHolder(),
			buildEmployeeHolder(),
			table,
			buildColumnAdapter(),
			buildLabelProvider()
		);

		Collection<Employee> employees = new ArrayList<Employee>();
		tableModel.addSelectionChangeListener(buildSelectionChangeListener(employees));

		// Create Employees
		String expectedName1    = "Dali_1";
		String expectedManager1 = "WTP_1";
		String expectedTitle1   = "plug-in_1";

		String expectedName2    = "Dali_2";
		String expectedManager2 = "WTP_2";
		String expectedTitle2   = "plug-in_2";

		String expectedName3    = "Dali_3";
		String expectedManager3 = "WTP_3";
		String expectedTitle3   = "plug-in_3";

		Employee employee1 = new Employee(expectedName1, expectedTitle1, expectedManager1);
		Employee employee2 = new Employee(expectedName2, expectedTitle2, expectedManager2);
		Employee employee3 = new Employee(expectedName3, expectedTitle3, expectedManager3);

		manager.addEmployee(employee1);
		manager.addEmployee(employee2);
		manager.addEmployee(employee3);

		// Test adding selected items
		table.setSelection(new int[] { 0, 2 });

		// It seems a manual selection doesn't send any selection event
		tableModel.tableSelectionChanged(null);

		Assert.assertNotNull("The collection of selected items can't be null", employees);
		Assert.assertEquals("The count of selected items should be 2", 2, employees.size());

		employees.remove(employee1);
		employees.remove(employee3);
		Assert.assertTrue("The selected items was not retrieved correctly", employees.isEmpty());

		// Test adding selected items
		employees.add(employee1);
		employees.add(employee3);
		table.deselectAll();

		// It seems a manual selection doesn't send any selection event
		tableModel.tableSelectionChanged(null);

		Assert.assertNotNull("The collection of selected items can't be null", employees);
		Assert.assertEquals("The count of selected items should be 0", 0, employees.size());
	}

	private void testTableItemProperties(Table table,
	                                     int index,
	                                     String expectedName,
	                                     String expectedManager,
	                                     String expectedTitle)
	{
		TableItem tableItem = table.getItem(index);
		Assert.assertNotNull("No TableItem was found for the Employee", tableItem);

		String actualName = tableItem.getText(TableColumnAdapter.NAME_COLUMN);
		Assert.assertEquals("TableItem[NAME_COLUMN] was not set correctly", expectedName, actualName);

		String actualTitle = tableItem.getText(TableColumnAdapter.TITLE_COLUMN);
		Assert.assertEquals("TableItem[TITLE_COLUMN] was not set correctly", expectedTitle, actualTitle);

		String actualManager = tableItem.getText(TableColumnAdapter.MANAGER_COLUMN);
		Assert.assertEquals("TableItem[MANAGER_COLUMN] was not set correctly", expectedManager, actualManager);
	}

	private class Employee extends AbstractModel {

		private String manager;
		private String name;
		private String title;

		static final String MANAGER_PROPERTY = "manager";
		static final String NAME_PROPERTY = "name";
		static final String TITLE_PROPERTY = "title";

		Employee(String name, String title, String manager) {
			super();

			this.name    = name;
			this.title   = title;
			this.manager = manager;
		}

		String getManager() {
			return manager;
		}

		String getName() {
			return name;
		}

		String getTitle() {
			return title;
		}

		void setManager(String manager) {
			String oldManager = this.manager;
			this.manager = manager;
			firePropertyChanged(MANAGER_PROPERTY, oldManager, manager);
		}

		void setName(String name) {
			String oldName = this.name;
			this.name = name;
			firePropertyChanged(NAME_PROPERTY, oldName, name);
		}

		void setTitle(String title) {
			String oldTitle = this.title;
			this.title = title;
			firePropertyChanged(TITLE_PROPERTY, oldTitle, title);
		}
	}

	private class Manager extends AbstractModel {

		private List<Employee> employees;

		static final String EMPLOYEES_LIST = "employees";

		Manager() {
			super();
			employees = new ArrayList<Employee>();
		}

		Employee addEmployee(Employee employee) {
			addItemToList(employee, employees, EMPLOYEES_LIST);
			return employee;
		}

		Employee addEmployee(String name, String title, String manager) {
			Employee employee = new Employee(name, title, manager);
			return addEmployee(employee);
		}

		void changeEmployees(List<Employee> newEmployees) {
			this.synchronizeList(newEmployees, this.employees, EMPLOYEES_LIST);
		}

		ListIterator<Employee> employees() {
			return new CloneListIterator<Employee>(employees);
		}

		int employeesSize() {
			return employees.size();
		}

		void moveEmployeeDown(Employee employee) {
			int index = employees.indexOf(employee);
			moveItemInList(index + 1, index, employees, EMPLOYEES_LIST);
		}

		void moveEmployees(Collection<Employee> employees, int targetIndex) {

			int sourceIndex = Integer.MAX_VALUE;

			for (Employee employee : employees) {
				sourceIndex = Math.min(sourceIndex, this.employees.indexOf(employee));
			}

			moveItemsInList(
				targetIndex,
				sourceIndex,
				employees.size(),
				this.employees,
				EMPLOYEES_LIST
			);
		}

		void moveEmployeeUp(Employee employee) {
			int index = employees.indexOf(employee);
			moveItemInList(index - 1, index, employees, EMPLOYEES_LIST);
		}

		void removeAllEmployees() {
			clearList(employees, EMPLOYEES_LIST);
		}

		void removeEmployee(Employee employee) {
			removeItemFromList(employee, employees, EMPLOYEES_LIST);
		}

		void removeEmployees(Collection<Employee> employees) {
			removeItemsFromList(employees.iterator(), this.employees, EMPLOYEES_LIST);
		}

		void replace(Employee oldEmployee, Employee newEmployee) {
			replaceItemInList(oldEmployee, newEmployee, employees, EMPLOYEES_LIST);
		}
	}

	private class TableColumnAdapter implements ColumnAdapter<Employee> {

		static final int COLUMN_COUNT = 3;
		static final int MANAGER_COLUMN = 2;
		static final int NAME_COLUMN = 0;
		static final int TITLE_COLUMN = 1;

		private WritablePropertyValueModel<String> buildManagerHolder(Employee subject) {
			return new PropertyAspectAdapter<Employee, String>(Employee.MANAGER_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getManager();
				}

				@Override
				protected void setValue_(String value) {
					subject.setManager(value);
				}
			};
		}

		private WritablePropertyValueModel<String> buildNameHolder(Employee subject) {
			return new PropertyAspectAdapter<Employee, String>(Employee.NAME_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}

				@Override
				protected void setValue_(String value) {
					subject.setName(value);
				}
			};
		}

		private WritablePropertyValueModel<String> buildTitleHolder(Employee subject) {
			return new PropertyAspectAdapter<Employee, String>(Employee.TITLE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getTitle();
				}

				@Override
				protected void setValue_(String value) {
					subject.setTitle(value);
				}
			};
		}

		public WritablePropertyValueModel<?>[] cellModels(Employee subject) {
			WritablePropertyValueModel<?>[] holders = new WritablePropertyValueModel<?>[3];
			holders[NAME_COLUMN] = buildNameHolder(subject);
			holders[TITLE_COLUMN] = buildTitleHolder(subject);
			holders[MANAGER_COLUMN] = buildManagerHolder(subject);
			return holders;
		}

		public int columnCount() {
			return COLUMN_COUNT;
		}

		public String columnName(int columnIndex) {
			return String.valueOf(columnIndex);
		}
	}

	private class TableLabelProvider extends LabelProvider
	                                 implements ITableLabelProvider {


		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			Employee employee = (Employee) element;

			if (columnIndex == TableColumnAdapter.NAME_COLUMN) {
				return employee.getName();
			}

			if (columnIndex == TableColumnAdapter.TITLE_COLUMN) {
				return employee.getTitle();
			}

			return employee.getManager();
		}
	}

	private class TableModel extends TableModelAdapter<Employee> {

		TableModel(ListValueModel<Employee> listHolder,
		           CollectionValueModel<Employee> selectedItemsHolder,
		           Table table,
		           ColumnAdapter<Employee> columnAdapter,
		           ITableLabelProvider labelProvider) {

			super(listHolder,
			      selectedItemsHolder,
			      table,
			      columnAdapter,
			      labelProvider);
		}

		TableModel(ListValueModel<Employee> listHolder,
		           WritablePropertyValueModel<Employee> selectedItemHolder,
		           Table table,
		           ColumnAdapter<Employee> columnAdapter,
		           ITableLabelProvider labelProvider) {

			super(listHolder,
					new PropertyCollectionValueModelAdapter<Employee>(selectedItemHolder),
			      table,
			      columnAdapter,
			      labelProvider);
		}

		@Override
		protected int indexOf(Employee item) {
			return super.indexOf(item);
		}

		@Override
		protected Collection<Employee> selectedItems() {
			return super.selectedItems();
		}

		@Override
		protected void tableSelectionChanged(SelectionEvent event) {
			super.tableSelectionChanged(event);
		}
	}
}
