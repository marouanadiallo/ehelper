package com.dialltay.ehelper.adapter.in.requestDTO;


public record SliceCursor(int nextPage, int prevPage, int size) {

    public static SliceCursor nextPage(int currentPage, int size) {
        return new SliceCursor(currentPage + 1, currentPage, size);
    }

    public static SliceCursor prevPage(int currentPage, int size) {
        return new SliceCursor(currentPage, Math.max(currentPage - 1, 0), size);
    }

    public static SliceCursor moveTo(int page, int size) {
        return new SliceCursor(page + 1, Math.max(page - 1, 0), size);
    }
}