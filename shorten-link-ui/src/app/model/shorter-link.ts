export class ShorterLink {
    id!: number;
    createdDate!: Date;
    modifiedDate!: Date;
    expiryDate!: Date;
    hash!: string;
    originalLink!: string;

    constructor(originalLink: string, expiryDate: Date) {
        this.expiryDate = expiryDate;
        this.originalLink = originalLink;
    }
}
