import { JSEncrypt } from 'jsencrypt'


// 加密公钥
let key =
  '-----BEGIN PUBLIC KEY-----' +
  'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+mEDzYLbKNB9rbOuvGgwdBUpPaHryRGarxBQppkOzlj+ouep8MMq1Xg7NBkjLOV2vnn4E5AVvX0XVOmBg8W5eNQ1uS1HCG2fie8BpXGgl1pWj/HYIrA2d/U7xxvMO8UMhAGfMdaGrPrGdZTr95pzL/q+VJZOcqSAgux/YEdu11wIDAQAB' +
  '-----END PUBLIC KEY-----'


export function rsaEncrypt(msg) {
    const encrypt = new JSEncrypt();
    encrypt.setPublicKey(key);
    return encrypt.encrypt(msg);
}
