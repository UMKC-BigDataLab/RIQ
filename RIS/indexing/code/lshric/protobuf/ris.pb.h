// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ris.proto

#ifndef PROTOBUF_ris_2eproto__INCLUDED
#define PROTOBUF_ris_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 2004000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2004001 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/generated_message_reflection.h>
// @@protoc_insertion_point(includes)

namespace ris {

// Internal implementation detail -- do not call these.
void  protobuf_AddDesc_ris_2eproto();
void protobuf_AssignDesc_ris_2eproto();
void protobuf_ShutdownFile_ris_2eproto();

class HashValue;
class LSHindex;

// ===================================================================

class HashValue : public ::google::protobuf::Message {
 public:
  HashValue();
  virtual ~HashValue();
  
  HashValue(const HashValue& from);
  
  inline HashValue& operator=(const HashValue& from) {
    CopyFrom(from);
    return *this;
  }
  
  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }
  
  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }
  
  static const ::google::protobuf::Descriptor* descriptor();
  static const HashValue& default_instance();
  
  void Swap(HashValue* other);
  
  // implements Message ----------------------------------------------
  
  HashValue* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const HashValue& from);
  void MergeFrom(const HashValue& from);
  void Clear();
  bool IsInitialized() const;
  
  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:
  
  ::google::protobuf::Metadata GetMetadata() const;
  
  // nested types ----------------------------------------------------
  
  // accessors -------------------------------------------------------
  
  // required string hash = 1;
  inline bool has_hash() const;
  inline void clear_hash();
  static const int kHashFieldNumber = 1;
  inline const ::std::string& hash() const;
  inline void set_hash(const ::std::string& value);
  inline void set_hash(const char* value);
  inline void set_hash(const char* value, size_t size);
  inline ::std::string* mutable_hash();
  inline ::std::string* release_hash();
  
  // required int32 freq = 2;
  inline bool has_freq() const;
  inline void clear_freq();
  static const int kFreqFieldNumber = 2;
  inline ::google::protobuf::int32 freq() const;
  inline void set_freq(::google::protobuf::int32 value);
  
  // @@protoc_insertion_point(class_scope:ris.HashValue)
 private:
  inline void set_has_hash();
  inline void clear_has_hash();
  inline void set_has_freq();
  inline void clear_has_freq();
  
  ::google::protobuf::UnknownFieldSet _unknown_fields_;
  
  ::std::string* hash_;
  ::google::protobuf::int32 freq_;
  
  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(2 + 31) / 32];
  
  friend void  protobuf_AddDesc_ris_2eproto();
  friend void protobuf_AssignDesc_ris_2eproto();
  friend void protobuf_ShutdownFile_ris_2eproto();
  
  void InitAsDefaultInstance();
  static HashValue* default_instance_;
};
// -------------------------------------------------------------------

class LSHindex : public ::google::protobuf::Message {
 public:
  LSHindex();
  virtual ~LSHindex();
  
  LSHindex(const LSHindex& from);
  
  inline LSHindex& operator=(const LSHindex& from) {
    CopyFrom(from);
    return *this;
  }
  
  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }
  
  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }
  
  static const ::google::protobuf::Descriptor* descriptor();
  static const LSHindex& default_instance();
  
  void Swap(LSHindex* other);
  
  // implements Message ----------------------------------------------
  
  LSHindex* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const LSHindex& from);
  void MergeFrom(const LSHindex& from);
  void Clear();
  bool IsInitialized() const;
  
  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:
  
  ::google::protobuf::Metadata GetMetadata() const;
  
  // nested types ----------------------------------------------------
  
  // accessors -------------------------------------------------------
  
  // repeated .ris.HashValue hashvalue = 1;
  inline int hashvalue_size() const;
  inline void clear_hashvalue();
  static const int kHashvalueFieldNumber = 1;
  inline const ::ris::HashValue& hashvalue(int index) const;
  inline ::ris::HashValue* mutable_hashvalue(int index);
  inline ::ris::HashValue* add_hashvalue();
  inline const ::google::protobuf::RepeatedPtrField< ::ris::HashValue >&
      hashvalue() const;
  inline ::google::protobuf::RepeatedPtrField< ::ris::HashValue >*
      mutable_hashvalue();
  
  // @@protoc_insertion_point(class_scope:ris.LSHindex)
 private:
  
  ::google::protobuf::UnknownFieldSet _unknown_fields_;
  
  ::google::protobuf::RepeatedPtrField< ::ris::HashValue > hashvalue_;
  
  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(1 + 31) / 32];
  
  friend void  protobuf_AddDesc_ris_2eproto();
  friend void protobuf_AssignDesc_ris_2eproto();
  friend void protobuf_ShutdownFile_ris_2eproto();
  
  void InitAsDefaultInstance();
  static LSHindex* default_instance_;
};
// ===================================================================


// ===================================================================

// HashValue

// required string hash = 1;
inline bool HashValue::has_hash() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void HashValue::set_has_hash() {
  _has_bits_[0] |= 0x00000001u;
}
inline void HashValue::clear_has_hash() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void HashValue::clear_hash() {
  if (hash_ != &::google::protobuf::internal::kEmptyString) {
    hash_->clear();
  }
  clear_has_hash();
}
inline const ::std::string& HashValue::hash() const {
  return *hash_;
}
inline void HashValue::set_hash(const ::std::string& value) {
  set_has_hash();
  if (hash_ == &::google::protobuf::internal::kEmptyString) {
    hash_ = new ::std::string;
  }
  hash_->assign(value);
}
inline void HashValue::set_hash(const char* value) {
  set_has_hash();
  if (hash_ == &::google::protobuf::internal::kEmptyString) {
    hash_ = new ::std::string;
  }
  hash_->assign(value);
}
inline void HashValue::set_hash(const char* value, size_t size) {
  set_has_hash();
  if (hash_ == &::google::protobuf::internal::kEmptyString) {
    hash_ = new ::std::string;
  }
  hash_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* HashValue::mutable_hash() {
  set_has_hash();
  if (hash_ == &::google::protobuf::internal::kEmptyString) {
    hash_ = new ::std::string;
  }
  return hash_;
}
inline ::std::string* HashValue::release_hash() {
  clear_has_hash();
  if (hash_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = hash_;
    hash_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}

// required int32 freq = 2;
inline bool HashValue::has_freq() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void HashValue::set_has_freq() {
  _has_bits_[0] |= 0x00000002u;
}
inline void HashValue::clear_has_freq() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void HashValue::clear_freq() {
  freq_ = 0;
  clear_has_freq();
}
inline ::google::protobuf::int32 HashValue::freq() const {
  return freq_;
}
inline void HashValue::set_freq(::google::protobuf::int32 value) {
  set_has_freq();
  freq_ = value;
}

// -------------------------------------------------------------------

// LSHindex

// repeated .ris.HashValue hashvalue = 1;
inline int LSHindex::hashvalue_size() const {
  return hashvalue_.size();
}
inline void LSHindex::clear_hashvalue() {
  hashvalue_.Clear();
}
inline const ::ris::HashValue& LSHindex::hashvalue(int index) const {
  return hashvalue_.Get(index);
}
inline ::ris::HashValue* LSHindex::mutable_hashvalue(int index) {
  return hashvalue_.Mutable(index);
}
inline ::ris::HashValue* LSHindex::add_hashvalue() {
  return hashvalue_.Add();
}
inline const ::google::protobuf::RepeatedPtrField< ::ris::HashValue >&
LSHindex::hashvalue() const {
  return hashvalue_;
}
inline ::google::protobuf::RepeatedPtrField< ::ris::HashValue >*
LSHindex::mutable_hashvalue() {
  return &hashvalue_;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace ris

#ifndef SWIG
namespace google {
namespace protobuf {


}  // namespace google
}  // namespace protobuf
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_ris_2eproto__INCLUDED
