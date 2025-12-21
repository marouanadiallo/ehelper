import {DataTable} from "simple-datatables";

const dataTable = new DataTable("#user-table", {
	searchable: false,
	fixedHeight: true,
    paging: false
});