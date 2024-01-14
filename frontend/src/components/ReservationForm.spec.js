import { shallowMount } from '@vue/test-utils';
import ReservationForm from '@/components/ReservationForm.vue';

describe('ReservationForm.vue', () => {

  it('renders the reservation form', () => {
    const wrapper = shallowMount(ReservationForm);
    expect(wrapper.find('form').exists()).toBe(true);
    expect(wrapper.find('input#name').exists()).toBe(true);
    expect(wrapper.find('input#date').exists()).toBe(true);
    expect(wrapper.find('input#email').exists()).toBe(true);
    expect(wrapper.find('input#numberOfPeople').exists()).toBe(true);
  });

  it('calls "createReservation" when form is submitted', async () => {
    const createReservationSpy = jest.spyOn(ReservationForm.methods, 'createReservation');
    const wrapper = shallowMount(ReservationForm);
    await wrapper.find('form').trigger('submit.prevent');
    expect(createReservationSpy).toHaveBeenCalled();
  });
  


  it('updates the number of people', async () => {
    const wrapper = shallowMount(ReservationForm);
    const numberOfPeopleInput = wrapper.find('input#numberOfPeople');
    await numberOfPeopleInput.setValue('4');
    expect(wrapper.vm.newReservation.numberOfPeople).toBe('4');
  });

  // Weitere Tests hier hinzufügen...
});
