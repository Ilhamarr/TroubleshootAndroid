package com.mobcom.troubleshoot.models.Login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("role")
	private String role;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("active")
	private String active;

	@SerializedName("locale")
	private String locale;

	@SerializedName("picture")
	private String picture;

	@SerializedName("password")
	private String password;

	@SerializedName("oauth_provider")
	private String oauthProvider;

	@SerializedName("accounts_id")
	private String accountsId;

	@SerializedName("oauth_id")
	private String oauthId;

	@SerializedName("modified_at")
	private String modifiedAt;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("nomor")
	private String nomor_hp;

	@SerializedName("alamat")
	private String alamat;

	public String getNomor_hp() {
		return nomor_hp;
	}

	public void setNomor_hp(String nomor_hp) {
		this.nomor_hp = nomor_hp;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getActive(){
		return active;
	}

	public void setLocale(String locale){
		this.locale = locale;
	}

	public String getLocale(){
		return locale;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return picture;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setOauthProvider(String oauthProvider){
		this.oauthProvider = oauthProvider;
	}

	public String getOauthProvider(){
		return oauthProvider;
	}

	public void setAccountsId(String accountsId){
		this.accountsId = accountsId;
	}

	public String getAccountsId(){
		return accountsId;
	}

	public void setOauthId(String oauthId){
		this.oauthId = oauthId;
	}

	public String getOauthId(){
		return oauthId;
	}

	public void setModifiedAt(String modifiedAt){
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedAt(){
		return modifiedAt;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
	public String toString() {
		return "LoginData{" +
						"role='" + role + '\'' +
						", lastName='" + lastName + '\'' +
						", createdAt='" + createdAt + '\'' +
						", active='" + active + '\'' +
						", locale='" + locale + '\'' +
						", picture='" + picture + '\'' +
						", password='" + password + '\'' +
						", oauthProvider='" + oauthProvider + '\'' +
						", accountsId='" + accountsId + '\'' +
						", oauthId='" + oauthId + '\'' +
						", modifiedAt='" + modifiedAt + '\'' +
						", firstName='" + firstName + '\'' +
						", email='" + email + '\'' +
						", nomor_hp='" + nomor_hp + '\'' +
						", alamat='" + alamat + '\'' +
						'}';
	}
}