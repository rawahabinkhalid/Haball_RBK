package com.haball.Distributor.ui.payments;

public class ProofOfPaymentViewFormModel {
    private String ID, POPID, ImageData, ImageType, CreatedDate, Title, FileTypeValue;

    public ProofOfPaymentViewFormModel(String ID, String POPID, String imageData, String imageType, String createdDate, String title, String fileTypeValue) {
        this.ID = ID;
        this.POPID = POPID;
        ImageData = imageData;
        ImageType = imageType;
        CreatedDate = createdDate;
        Title = title;
        FileTypeValue = fileTypeValue;
    }

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFileTypeValue() {
        return FileTypeValue;
    }

    public void setFileTypeValue(String fileTypeValue) {
        FileTypeValue = fileTypeValue;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPOPID() {
        return POPID;
    }

    public void setPOPID(String POPID) {
        this.POPID = POPID;
    }

    public String getImageData() {
        return ImageData;
    }

    public void setImageData(String imageData) {
        ImageData = imageData;
    }
}
