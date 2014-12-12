package models.ad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name = "images")
public class Image extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Finder<Long, Image> find = new Finder<Long, Image>(
			Long.class, Image.class);

	@Id
	public Long id;

	public byte[] content;

	@Constraints.Required
	public String contentType;

	// TODO

	@SuppressWarnings("static-access")
	public static String byteToBase64(byte[] data) {
		return Base64.encodeBase64String(data);
	}
}
